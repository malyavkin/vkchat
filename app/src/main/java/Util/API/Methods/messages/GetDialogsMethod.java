package Util.API.Methods.messages;

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

    public HashMap<String, Dialog> parseResult(JSONObject response) throws JSONException {
        HashMap<String, Dialog> dialogModels = new HashMap<>();
        JSONObject oResponse = response.getJSONObject("response");
        JSONArray oItems = oResponse.getJSONArray("items");

        for (int i = 0; i < oItems.length(); i++) {
            JSONObject oItem = oItems.getJSONObject(i);
            JSONObject oMessage = oItem.getJSONObject("message");
            String title = oMessage.getString("title");
            String message = oMessage.getString("body");
            int user_id = oMessage.getInt("user_id");
            int lastMessageDate = oMessage.getInt("date");

            int entity_id;
            String type;

            if (oMessage.has("chat_id")) {
                type = "conf";
                entity_id = oMessage.getInt("chat_id");
            } else if (user_id > 0) {
                type = "chat";
                entity_id = user_id;
            } else {
                type = "community";
                entity_id = -user_id;
            }

            Dialog d = new Dialog(type, entity_id, message, title, lastMessageDate);
            dialogModels.put(d.id, d);
        }

        return dialogModels;
    }
}
