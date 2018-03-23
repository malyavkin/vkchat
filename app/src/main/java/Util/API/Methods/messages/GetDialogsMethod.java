package Util.API.Methods.messages;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Dialog.Dialog;
import Util.API.Method;

public class GetDialogsMethod extends Method<Dialog> {
    private final static String TAG = "GetDialogsMethod";

    public GetDialogsMethod(int offset, int count, boolean unreadOnly) {

        params = new HashMap<>();
        params.put("offset", String.valueOf(offset));
        params.put("count", String.valueOf(count));
        if (unreadOnly) {
            params.put("unread", "1");
        }

    }

    @Override
    public String getMethod() {
        return "messages.getDialogs";
    }

    public SparseArray<Dialog> parseResult(JSONObject response) throws JSONException {
        SparseArray<Dialog> dialogModels = new SparseArray<>();
        JSONObject oResponse = response.getJSONObject("response");
        JSONArray oItems = oResponse.getJSONArray("items");

        for (int i = 0; i < oItems.length(); i++) {
            JSONObject oItem = oItems.getJSONObject(i);
            JSONObject oMessage = oItem.getJSONObject("message");
            String title = oMessage.getString("title");
            String message = oMessage.getString("body");
            int id = oMessage.has("chat_id")
                    ? oMessage.getInt("chat_id")
                    : oMessage.getInt("user_id");

            if (title.equals("")) {
                title = "Dialog " + id;
            }

            dialogModels.append(id, new Dialog(message, title, id));
        }

        return dialogModels;
    }
}
