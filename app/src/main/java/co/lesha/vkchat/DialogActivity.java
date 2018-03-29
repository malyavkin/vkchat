package co.lesha.vkchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;

import Persistence.Entities.Dialog.DialogType;
import Persistence.Entities.Group.Group;
import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.Constants;
import Util.Listener;
import Util.Service.Service;

public class DialogActivity extends AppCompatActivity {
    String token;
    APIRequestBuilder api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Intent I = getIntent();
        token = I.getStringExtra(Constants.TOKEN);
        DialogType type = DialogType.valueOf(I.getStringExtra("type"));
        final String id = I.getStringExtra("id");
        api = new APIRequestBuilder(token);

        TextView text_api = findViewById(R.id.text_api);
        text_api.setText(token);
        TextView text_type = findViewById(R.id.text_type);
        text_type.setText(type.toString());
        TextView text_id = findViewById(R.id.text_id);
        text_id.setText(id);

        switch (type) {
            case CONF:
                break;
            case PERSON:
                Service.getInstance().getNameCache().getUser(id, new Listener<HashMap<String, User>>() {
                    @Override
                    public void call(HashMap<String, User> param) {
                        setTitle(param.get(id).fullname());
                    }
                });
                break;
            case COMMUNITY:
                Service.getInstance().getNameCache().getGroup(id, new Listener<HashMap<String, Group>>() {
                    @Override
                    public void call(HashMap<String, Group> param) {
                        setTitle(param.get(id).name);
                    }
                });
        }

    }
}
