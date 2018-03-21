package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import java.util.ArrayList;

import Adapter.ChatListAdapter;
import Persistence.Entities.Dialog.Dialog;
import Util.APIRequestBuilder;
import Util.Constants;
import Util.DialogSequentialDownloader;
import Util.Listener;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView rv;
    APIRequestBuilder api;
    SparseArray<Dialog> dialogs = new SparseArray<>();

    public static <C> ArrayList<C> asList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        ArrayList<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }

    /**
     * Обновляет вьюху новым адаптером
     */
    void updateView() {
        ChatListAdapter adapter = new ChatListAdapter(this, asList(dialogs));

        if (rv.getAdapter() == null) {
            rv.setAdapter(adapter);
        } else {
            rv.swapAdapter(adapter, false);
        }
    }

    /**
     *
     */
    void obtainDialogs() {
        DialogSequentialDownloader dsd = new DialogSequentialDownloader(
                getApplicationContext(),
                api,
                new Listener<SparseArray<Dialog>>() {

                    @Override
                    public void call(SparseArray<Dialog> newDialogs) {
                        dialogs = newDialogs;
                        updateView();
                    }
                }
        );

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
        obtainDialogs();
    }
}
