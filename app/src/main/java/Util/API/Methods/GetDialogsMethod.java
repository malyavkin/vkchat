package Util.API.Methods;

import android.content.Context;
import android.util.SparseArray;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Persistence.Entities.Dialog.Dialog;
import Persistence.Entities.Model;
import Util.API.Method;
import Util.APIRequestBuilder;
import Util.KV;

/**
 * Created by lich on 2018-03-20.
 */

public class GetDialogsMethod implements Method {

    private ArrayList<KV> params;
    private final static String method = "messages.getDialogs";
    public GetDialogsMethod(int offset, int count, boolean unreadOnly) {

        params = new ArrayList<>();
        params.add(new KV("offset", offset));
        params.add(new KV("count", count));
        if(unreadOnly) {
            params.add(new KV("unread", "1"));
        }

    }

    @Override
    public ArrayList<KV> getParams() {
        return this.params;
    }

    @Override
    public String getMethod() {
        return GetDialogsMethod.method;
    }

    private void obtainAllDialogsWithCallback(Response.Listener<JSONObject> listener,
                                              Response.ErrorListener errorListener,
                                              APIRequestBuilder api,
                                              Context ctx) {
        final int offset = 0;
        final int count = 10;
        final SparseArray<Dialog> dialogs = new SparseArray<>();
        obtainDialogsWithCallback(
                offset,
                count,
                true,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Dialog> newDialogs = parseResult(response);
                        for (Dialog dialog : newDialogs) {
                            dialogs.append(dialog.getId(), dialog);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },
                api,
                ctx
        );

    }

    private void obtainDialogsWithCallback(final int offset,
                                           int count,
                                           boolean unreadOnly,
                                           Response.Listener<JSONObject> listener,
                                           Response.ErrorListener errorListener,
                                           APIRequestBuilder api,
                                           Context ctx
    ) {
        final GetDialogsMethod method = new GetDialogsMethod(offset, count, unreadOnly);
        String s = api.makeRequestUrl(method);
        RequestQueue q = Volley.newRequestQueue(ctx);

        q.add(new JsonObjectRequest(s, null, listener, errorListener
        ));
    }

    @Override
    public ArrayList<Dialog> parseResult(JSONObject response) {
        ArrayList<Dialog> dialogModels = new ArrayList<>();
        try {
            JSONObject oResponse = response.getJSONObject("response");
            JSONArray oItems= oResponse.getJSONArray("items");

            for (int i = 0; i < oItems.length(); i++) {
                JSONObject oItem = oItems.getJSONObject(i);
                JSONObject oMessage = oItem.getJSONObject("message");
                String title = "Title " + i;
                String message = oMessage.getString("body");
                int id = oMessage.has("chat_id")
                        ? oMessage.getInt("chat_id")
                        : oMessage.getInt("user_id");
                dialogModels.add(id, new Dialog(message, title, id ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialogModels;
    }
}
