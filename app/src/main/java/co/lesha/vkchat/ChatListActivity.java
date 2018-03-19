package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.ChatListAdapter;
import Persistence.Entities.Dialog.Dialog;
import Util.API.Methods.GetDialogsMethod;
import Util.APIRequestBuilder;
import Util.Constants;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView rv;
    APIRequestBuilder api;

    void populateDialogs(ArrayList<Dialog> dialogs) {
        ChatListAdapter adapter = new ChatListAdapter(this, dialogs);
        rv.setAdapter(adapter);
    }



    void obtainDialogs(int offset, int count, boolean unreadOnly) {

        final GetDialogsMethod method = new GetDialogsMethod(offset, count, unreadOnly);
        String s = api.makeRequestUrl(method);
        RequestQueue q = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(s, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("tag", response.toString());

                    try {
                        populateDialogs(method.parseResult(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
        );

        q.add(jsonObjectRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        rv = findViewById(R.id.chat_list_recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Intent I = getIntent();
        String token = I.getStringExtra(Constants.TOKEN);
        api = new APIRequestBuilder(token);
        obtainDialogs(0, 10, false);


    }
}
