package Util.Downloader.Downloaders;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Persistence.Entities.User.User;
import Util.API.Method;
import Util.API.Methods.users.GetMethod;
import Util.Downloader.Downloader;
import Util.Listener;

public class UserDownloader extends Downloader<User> {
    private final static String TAG = "UserDownloader";
    private List<String> ids;

    public UserDownloader(Listener<HashMap<String, User>> listener,
                          String id) {
        super(listener);
        this.ids = Collections.singletonList(id);
        this.ids.add(id);
        run();
    }

    public UserDownloader(Listener<HashMap<String, User>> listener,
                          List<String> users) {
        super(listener);
        ids = users;
        run();
    }

    @Override
    protected void processResponse(JSONObject response, Method<User> method) {

        try {
            HashMap<String, User> newUsers = method.parseResult(response);
            items.putAll(newUsers);

        } catch (JSONException e) {
            requestQueue.add(buildRequest(method));
            Log.e(TAG, "Cannot process response");
            e.printStackTrace();
        }
    }

    @Override
    protected Method<User> getInitialParams() {
        return new GetMethod(this.ids);
    }
}
