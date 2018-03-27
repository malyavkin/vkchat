package Util.API.Methods.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import Persistence.Entities.User.User;
import Util.API.Method;

public class GetMethod extends Method<User> {

    public GetMethod(String id) {
        params = new HashMap<>();
        params.put("user_ids", id);
    }

    public GetMethod(List<String> ids) {
        params = new HashMap<>();
        //TODO: this

        StringBuilder idsString = new StringBuilder();
        for (String id : ids) {
            if (idsString.length() > 0) {
                idsString.append(",");
            }
            idsString.append(id);
        }

        params.put("user_ids", idsString.toString());
    }

    @Override
    public String getMethod() {
        return "users.get";
    }

    @Override
    public HashMap<String, User> parseResult(JSONObject response) throws JSONException {
        HashMap<String, User> users = new HashMap<>();

        JSONArray oResponse = response.getJSONArray("response");
        for (int i = 0; i < oResponse.length(); i++) {
            JSONObject oUser = oResponse.getJSONObject(i);

            User u = new User(
                    oUser.getInt("id"),
                    oUser.getString("first_name"),
                    oUser.getString("last_name")
            );
            users.put(oUser.getString("id"), u);

        }

        return users;
    }


}
