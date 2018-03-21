package Util.API.Methods;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Dialog.Dialog;
import Util.API.Method;

/**
 * Created by lich on 2018-03-20.
 */

public class GetDialogsMethod extends Method<Dialog> {

    private HashMap<String, String> params;
    private final static String method = "messages.getDialogs";
    public GetDialogsMethod(int offset, int count, boolean unreadOnly) {

        params = new HashMap<>();
        params.put("offset", String.valueOf(offset));
        params.put("count", String.valueOf(count));
        if(unreadOnly) {
            params.put("unread", "1");
        }

    }

    @Override
    public HashMap<String, String> getParams() {
        return this.params;
    }

    @Override
    public String getMethod() {
        return GetDialogsMethod.method;
    }

    public SparseArray<Dialog> parseResult(JSONObject response) {
        SparseArray<Dialog> dialogModels = new SparseArray<>();
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
                dialogModels.append(id, new Dialog(message, title, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialogModels;
    }
}
