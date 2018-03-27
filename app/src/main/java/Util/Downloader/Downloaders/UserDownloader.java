package Util.Downloader.Downloaders;

import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.API.Methods.users.GetMethod;
import Util.Downloader.Downloader;
import Util.Listener;

public class UserDownloader extends Downloader<User> {
    private final static String TAG = "UserDownloader";
    private List<String> ids;
    public UserDownloader(RequestQueue q,
                          APIRequestBuilder api,
                          Listener<HashMap<String, User>> listener,
                          String id) {
        super(q, api, listener);
        this.ids = Arrays.asList(id);
        this.ids.add(id);
    }

    public UserDownloader(RequestQueue q,
                          APIRequestBuilder api,
                          Listener<HashMap<String, User>> listener,
                          List<String> users) {
        super(q, api, listener);
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
