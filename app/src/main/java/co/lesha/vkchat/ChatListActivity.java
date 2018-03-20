package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.ChatListAdapter;
import Adapter.OnBottomReachedListener;
import Persistence.Entities.Dialog.Dialog;
import Util.API.Methods.GetDialogsMethod;
import Util.APIRequestBuilder;
import Util.Constants;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView rv;
    APIRequestBuilder api;
    ArrayList<Dialog> dialogs = new ArrayList<>();

    OnBottomReachedListener onBottomReachedListener = new OnBottomReachedListener() {
        @Override
        public void onBottomReached(int position) {
            Toast.makeText(getApplicationContext(), "onBottomReached", Toast.LENGTH_SHORT).show();
            obtainDialogs(dialogs.size(), 10, false);
        }
    };


    /**
     * Обновляет вьюху новым адаптером
     */
    void updateView() {
        ChatListAdapter adapter = new ChatListAdapter(this, dialogs);
        adapter.setOnBottomReachedListener(onBottomReachedListener);

        if (rv.getAdapter() == null) {
            rv.setAdapter(adapter);
        } else {
            rv.swapAdapter(adapter, false);
        }
    }

    /**
     * Обновляет список диалогов
     * @param newDialogs массив новых данных
     * @param startingWith
     */
    void populateDialogs(ArrayList<Dialog> newDialogs, int startingWith) {
        dialogs.ensureCapacity(startingWith + newDialogs.size());
        dialogs.addAll(startingWith, newDialogs);

        updateView();
    }

    /**
     * Создает слушатель запроса с диалогами
     * @param method
     * @param offset
     * @return
     */
    Response.Listener<JSONObject> getOnDialogsObtainedListener(final GetDialogsMethod method, final int offset){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("tag", response.toString());
                try {
                    populateDialogs(method.parseResult(response), offset);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

    };

    /**
     * Создает слушатель ошибки
     * @return
     */
    Response.ErrorListener getOnErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }




    /**
     * Загружает порцию диалогов
     * @param offset
     * @param count
     * @param unreadOnly
     */
    void obtainDialogs(final int offset, int count, boolean unreadOnly) {
        final GetDialogsMethod method = new GetDialogsMethod(offset, count, unreadOnly);
        String s = api.makeRequestUrl(method);
        RequestQueue q = Volley.newRequestQueue(this);

        q.add(new JsonObjectRequest(s, null,
                getOnDialogsObtainedListener(method, offset),
                getOnErrorListener()
        ));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        rv = findViewById(R.id.chat_list_recycler);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Intent I = getIntent();
        String token = I.getStringExtra(Constants.TOKEN);
        api = new APIRequestBuilder(token);
        obtainDialogs(0, 10, false);
    }
}
