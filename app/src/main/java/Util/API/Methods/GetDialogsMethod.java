package Util.API.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Persistence.Entities.Dialog.Dialog;
import Persistence.Entities.Model;
import Util.API.Method;
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

    @Override
    public ArrayList<Dialog> parseResult(JSONObject response) throws JSONException {
        JSONObject oResponse = response.getJSONObject("response");
        JSONArray oItems= oResponse.getJSONArray("items");
        ArrayList<Dialog> dialogModels = new ArrayList<>();
        for (int i = 0; i < oItems.length(); i++) {
             JSONObject oItem = oItems.getJSONObject(i);
             JSONObject oMessage = oItem.getJSONObject("message");
             String title = "Title " + i;
             String message = oMessage.getString("body");
            dialogModels.add(new Dialog(message, title));
        }
        return dialogModels;
    }
}
