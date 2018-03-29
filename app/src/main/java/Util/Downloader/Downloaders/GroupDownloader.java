package Util.Downloader.Downloaders;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Persistence.Entities.Group.Group;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.API.Methods.groups.GetById;
import Util.Downloader.Downloader;
import Util.Listener;

public class GroupDownloader extends Downloader<Group> {
    private final static String TAG = "GroupDownloader";
    private List<String> ids;

    public GroupDownloader(APIRequestBuilder api,
                           Listener<HashMap<String, Group>> listener,
                           String id) {
        super(api, listener);
        this.ids = Arrays.asList(id);
        this.ids.add(id);
    }

    public GroupDownloader(APIRequestBuilder api,
                           Listener<HashMap<String, Group>> listener,
                           List<String> users) {
        super(api, listener);
        ids = users;
        run();
    }

    @Override
    protected void processResponse(JSONObject response, Method<Group> method) {

        try {
            HashMap<String, Group> newUsers = method.parseResult(response);
            items.putAll(newUsers);

        } catch (JSONException e) {
            requestQueue.add(buildRequest(method));
            Log.e(TAG, "Cannot process response");
            e.printStackTrace();
        }
    }

    @Override
    protected Method<Group> getInitialParams() {
        return new GetById(this.ids);
    }
}
