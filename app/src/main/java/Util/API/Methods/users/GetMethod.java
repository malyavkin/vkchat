package Util.API.Methods.users;

import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.User.User;
import Util.API.Method;

public class GetMethod extends Method<User> {

    public GetMethod(int id) {
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
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
