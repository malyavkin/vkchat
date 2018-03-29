package Util.Downloader.Downloaders;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Dialog.Dialog;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.API.Methods.messages.GetDialogsMethod;
import Util.Downloader.SequentialDownloader;
import Util.Listener;

public class DialogSequentialDownloader extends SequentialDownloader<Dialog> {
    private final static String TAG = "DialogSeqDl";

    public DialogSequentialDownloader(APIRequestBuilder api,
                                      Listener<HashMap<String, Dialog>> listener) {
        super(api, listener);
        run();
    }

    @Override
    protected GetDialogsMethod getInitialParams() {
        return new GetDialogsMethod(0, 10, false);
    }

    @Override
    protected GetDialogsMethod getParamsForNextRequest(JSONObject response, Method<Dialog> method) throws JSONException {
        final int count = 10;
        final boolean unreadOnly = false;

        int oldOffset = Integer.valueOf(method.getParams().get("offset"));
        JSONObject oResponse = response.getJSONObject("response");
        int totalDialogs = oResponse.getInt("count");
        int receivedDialogsCount = oResponse.getJSONArray("items").length();
        int newOffset = oldOffset + receivedDialogsCount;
        if (newOffset >= totalDialogs) {
            return null;
        }
        Log.d(TAG, "Downloading dialogs" + String.valueOf(newOffset) + " +> " + String.valueOf(count));
        return new GetDialogsMethod(newOffset, count, unreadOnly);

    }

    @Override
    protected void processResponse(JSONObject response, Method<Dialog> method) {
        try {
            HashMap<String, Dialog> newDialogs = method.parseResult(response);
            items.putAll(newDialogs);

        } catch (JSONException e) {
            Log.e(TAG, "Cannot process response, restarting request");
            requestQueue.add(buildRequest(method));
            e.printStackTrace();
        }

    }
}
