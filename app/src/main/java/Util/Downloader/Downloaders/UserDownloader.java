package Util.Downloader.Downloaders;

import android.util.Log;
import android.util.SparseArray;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.API.Methods.users.GetMethod;
import Util.Downloader.Downloader;
import Util.Listener;

public class UserDownloader extends Downloader<User> {
    private final static String TAG = "UserDownloader";
    private ArrayList<Integer> ids;
    public UserDownloader(RequestQueue q,
                          APIRequestBuilder api,
                          Listener<SparseArray<User>> listener,
                          int id) {
        super(q, api, listener);
        this.ids = new ArrayList<>();
        this.ids.add(id);
    }

    public UserDownloader(RequestQueue q,
                          APIRequestBuilder api,
                          Listener<SparseArray<User>> listener,
                          ArrayList<Integer> users) {
        super(q, api, listener);
        this.ids = users;
    }

    @Override
    protected void processResponse(JSONObject response, Method<User> method) {

        try {
            SparseArray<User> newUsers = method.parseResult(response);

            for (int i = 0; i < newUsers.size(); i++) {
                int key = newUsers.keyAt(i);
                items.append(key, newUsers.valueAt(i));
            }

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
