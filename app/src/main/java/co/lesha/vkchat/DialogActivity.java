package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import Util.API.APIRequestBuilder;
import Util.Constants;
import Util.Network.Queue;

public class DialogActivity extends AppCompatActivity {
    String token;
    APIRequestBuilder api;
    RequestQueue q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Intent I = getIntent();
        token = I.getStringExtra(Constants.TOKEN);
        String type = I.getStringExtra("type");
        String id = I.getStringExtra("id");
        q = Queue.getInstance().getQueue();
        api = new APIRequestBuilder(token);

        TextView text_api = findViewById(R.id.text_api);
        text_api.setText(token);
        TextView text_type = findViewById(R.id.text_type);
        text_type.setText(type);
        TextView text_id = findViewById(R.id.text_id);
        text_id.setText(id);

    }
}
