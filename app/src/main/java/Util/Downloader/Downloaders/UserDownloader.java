package Util.Downloader.Downloaders;

import android.util.Log;
import android.util.SparseArray;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import Persistence.Entities.User.User;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.Downloader.Downloader;
import Util.Listener;

public class UserDownloader extends Downloader<User> {
    private final static String TAG = "UserDownloader";

    public UserDownloader(RequestQueue q,
                          APIRequestBuilder api,
                          Listener<SparseArray<User>> listener) {
        super(q, api, listener);
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
}
