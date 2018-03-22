package Util.Downloader;

import android.util.Log;
import android.util.SparseArray;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import Persistence.Entities.Dialog.Dialog;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.API.Methods.messages.GetDialogsMethod;
import Util.Listener;

/**
 * Created by amalyavkin on 21/03/2018.
 */

public class DialogSequentialDownloader extends SequentialDownloader<Dialog> {
    private final static String TAG = "DialogSeqDl";

    public DialogSequentialDownloader(RequestQueue q,
                                      APIRequestBuilder api,
                                      Listener<SparseArray<Dialog>> listener) {
        super(q, api, listener);
    }

    @Override
    GetDialogsMethod getInitialParams() {
        return new GetDialogsMethod(0, 10, false);
    }

    @Override
    GetDialogsMethod getParamsForNextRequest(JSONObject response, Method<Dialog> method) throws JSONException {
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
        return new GetDialogsMethod(newOffset, count, unreadOnly);

    }

    @Override
    void processResponse(JSONObject response, Method<Dialog> method) {
        try {
            SparseArray<Dialog> newDialogs = method.parseResult(response);

            for (int i = 0; i < newDialogs.size(); i++) {
                int key = newDialogs.keyAt(i);
                items.append(key, newDialogs.valueAt(i));
            }

        } catch (JSONException e) {
            Log.e(TAG, "Cannot process response, restarting request");
            requestQueue.add(buildRequest(method));
            e.printStackTrace();
        }

    }
}
