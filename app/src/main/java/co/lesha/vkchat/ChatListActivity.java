package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import Adapter.ChatListAdapter;
import Persistence.Entities.Dialog.Dialog;
import Persistence.Entities.Dialog.DialogType;
import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.Constants;
import Util.Downloader.Downloaders.DialogSequentialDownloader;
import Util.Downloader.Downloaders.UserDownloader;
import Util.Listener;
import Util.Network.Queue;

public class ChatListActivity extends AppCompatActivity {
    private static String TAG = "ChatListActivity";
    private RecyclerView rv;
    private APIRequestBuilder api;
    private HashMap<String, Dialog> dialogs = new HashMap<>();
    private RequestQueue q;
    private Listener<Dialog> onDialogItemClickListener;
    private String token;

    private static <C> List<C> asList(HashMap<String, C> hashMap) {
        if (hashMap == null) return null;
        List<C> arrayList = new ArrayList<>(hashMap.size());
        arrayList.addAll(hashMap.values());
        return arrayList;
    }

    /**
     * Обновляет вьюху новым адаптером
     */
    private void updateView() {

        List<Dialog> sortedDialogs = asList(dialogs);
        Collections.sort(sortedDialogs, new MessageDateComparator());

        ChatListAdapter adapter = new ChatListAdapter(sortedDialogs, onDialogItemClickListener);

        if (rv.getAdapter() == null) {
            rv.setAdapter(adapter);
        } else {
            rv.swapAdapter(adapter, false);
        }
    }

    private void bootstrapUsers() {
        ArrayList<String> personIds = new ArrayList<>();
        for (Dialog d : dialogs.values()) {
            if (d.type == DialogType.PERSON) {
                personIds.add(d.entity_id);
            }
        }

        new UserDownloader(
                q,
                api,
                new Listener<HashMap<String, User>>() {
                    @Override
                    public void call(HashMap<String, User> param) {
                        for (String key : param.keySet()) {
                            String fullname = param.get(key).fullname();
                            dialogs.get(DialogType.PERSON + "_" + key).chatTitle = fullname;
                        }
                        updateView();
                    }
                },
                personIds
        );
    }

    /**
     *
     */
    private void obtainDialogs() {
        new DialogSequentialDownloader(
                q,
                api,
                new Listener<HashMap<String, Dialog>>() {

                    @Override
                    public void call(HashMap<String, Dialog> newDialogs) {
                        dialogs = newDialogs;
                        bootstrapUsers();
                    }
                }
        );

    }

    private void onDialogItemClick(Dialog d) {
        Log.d(TAG, "Hello " + d.chatTitle + String.valueOf(d.entity_id));
        Intent I = new Intent(ChatListActivity.this, DialogActivity.class);
        I.putExtra(Constants.TOKEN, token);
        I.putExtra("type", d.type.toString());
        I.putExtra("id", d.entity_id);
        startActivity(I);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        rv = findViewById(R.id.chat_list_recycler);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Intent I = getIntent();
        token = I.getStringExtra(Constants.TOKEN);
        api = new APIRequestBuilder(token);
        q = Queue.getInstance().getQueue();

        onDialogItemClickListener = new Listener<Dialog>() {
            @Override
            public void call(Dialog param) {
                onDialogItemClick(param);
            }
        };

        obtainDialogs();
    }
}

class MessageDateComparator implements Comparator<Dialog> {
    @Override
    public int compare(Dialog d1, Dialog d2) {
        return d2.lastMessageDate - d1.lastMessageDate;
    }
}