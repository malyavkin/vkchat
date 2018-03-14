package co.lesha.vkchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.ChatListAdapter;
import Persistence.Entities.Dialog.Dialog;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        rv = findViewById(R.id.chat_list_recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Dialog> dialogModels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            dialogModels.add(new Dialog("Title " + i, "Message " + i));
        }
        ChatListAdapter adapter = new ChatListAdapter(this, dialogModels);
        rv.setAdapter(adapter);

    }
}
