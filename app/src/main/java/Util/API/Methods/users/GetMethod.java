package Util.API.Methods.messages;

import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.User.User;
import Util.API.Method;

/**
 * Created by lich on 2018-03-20.
 */

public class GetMethod extends Method<User> {
    private HashMap<String, String> params;

    public GetMethod(int id) {
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
    }

    @Override
    public HashMap<String, String> getParams() {
        return params;
    }

    @Override
    public String getMethod() {
        return "users.get";
    }

    @Override
    public SparseArray<User> parseResult(JSONObject response) throws JSONException {
        return null;
    }


}
