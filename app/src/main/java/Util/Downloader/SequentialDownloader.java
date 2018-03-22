package Util.Downloader;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Util.API.Method;

/**
 * Created by amalyavkin on 21/03/2018.
 */

public abstract class SequentialDownloader<T> {
    abstract Method<T> getParamsForNextRequest(JSONObject response, Method<T> method) throws JSONException;

    abstract Method<T> getInitialParams();

    abstract void processError(VolleyError error, Method<T> method);

    abstract JsonObjectRequest buildRequest(final Method<T> method);

    abstract void processResponse(JSONObject response, Method<T> method);
}
