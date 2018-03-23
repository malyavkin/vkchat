package Util.API;

import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Method is a class used to hold all necessary parameters for API calls and processing results
 * @param <T> Type of entity that is being retrieved
 */
public abstract class Method<T> {
    protected HashMap<String, String> params;

    public HashMap<String, String> getParams() {
        return this.params;
    }

    public abstract String getMethod();

    public abstract SparseArray<T> parseResult(JSONObject response) throws JSONException;
}
