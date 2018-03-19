package Util.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Persistence.Entities.Model;
import Util.KV;

/**
 * Created by lich on 2018-03-20.
 */

public interface Method<T> {
    public ArrayList<KV> getParams();
    public String getMethod();
    public ArrayList<T> parseResult(JSONObject response) throws JSONException;
}
