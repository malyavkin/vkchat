package Util;

import android.content.Context;
import android.util.SparseArray;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Persistence.Entities.Dialog.Dialog;
import Util.API.Method;
import Util.API.Methods.GetDialogsMethod;

/**
 * Created by amalyavkin on 21/03/2018.
 */

public class DialogSequentialDownloader extends SequentialDownloader<Dialog> {

    private final APIRequestBuilder api;
    private SparseArray<Dialog> dialogs;
    private Listener<SparseArray<Dialog>> listener;
    private RequestQueue q;


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

    public DialogSequentialDownloader(Context ctx,
                                      APIRequestBuilder api,
                                      Listener<SparseArray<Dialog>> listener) {
        this.api = api;
        dialogs = new SparseArray<>();
        q = Volley.newRequestQueue(ctx);
        this.listener = listener;
        run();
    }

    private void onFinish() {
        listener.call(dialogs);
    }

    @Override
    void processResponse(JSONObject response, Method<Dialog> method) {

        try {
            SparseArray<Dialog> newDialogs = method.parseResult(response);

            for (int i = 0; i < newDialogs.size(); i++) {
                int key = newDialogs.keyAt(i);
                dialogs.append(key, newDialogs.valueAt(i));
            }

            GetDialogsMethod newMethod = getParamsForNextRequest(response, method);
            if (newMethod != null) {
                q.add(buildRequest(newMethod));
            } else {
                onFinish();
            }

        } catch (JSONException e) {
            q.add(buildRequest(method));
            e.printStackTrace();
        }


    }

    @Override
    void processError(VolleyError error, Method<Dialog> method) {
        q.add(buildRequest(method));
    }

    @Override
    JsonObjectRequest buildRequest(final Method<Dialog> method) {
        String s = api.makeRequestUrl(method);
        return new JsonObjectRequest(s, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        processResponse(response, method);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        processError(error, method);
                    }
                }

        );
    }


    private void run() {

        final GetDialogsMethod method = getInitialParams();
        q.add(buildRequest(method));
    }


}
