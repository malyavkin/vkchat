package Util.API.Methods.messages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Message.Message;
import Util.API.Method;

/**
 * Created by amalyavkin on 30/03/2018.
 */

public class GetHistoryMethod extends Method<Message> {

    public GetHistoryMethod(int offset, int count, int peer_id) {
        params = new HashMap<>();
        params.put("offset", String.valueOf(offset));
        params.put("count", String.valueOf(count));
        params.put("peer_id", String.valueOf(peer_id));
    }

    @Override
    public String getMethod() {
        return "messages.getHistory";
    }

    @Override
    public HashMap<String, Message> parseResult(JSONObject response) throws JSONException {
        return null;
    }
}
