package Util.Downloader;

import android.util.Log;
import android.util.SparseArray;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import Persistence.Entities.Model;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.Listener;

/**
 * SequentialDownloader is a class used to download portions of data sequentially
 * @param <T>
 */
public abstract class SequentialDownloader<T extends Model> extends Downloader<T> {
    private final static String TAG = "SequentialDownloader";

    protected SequentialDownloader(RequestQueue q, APIRequestBuilder api, Listener<SparseArray<T>> listener) {
        super(q, api, listener);
        requestQueue.add(buildRequest(getInitialParams()));
    }

    /**
     * @param response current response
     * @param method   current set of parameters
     * @return set of parameters for the next request given current parameters and response
     * @throws JSONException {response} is JSONObject, so there's that
     */
    protected abstract Method<T> getParamsForNextRequest(JSONObject response, Method<T> method) throws JSONException;

    /**
     * get initial parameters
     *
     * @return set of parameters for the first request
     */
    protected abstract Method<T> getInitialParams();

    @Override
    void onResponseHandler(JSONObject response, Method<T> method) {
        processResponse(response, method);
        afterProcessResponse(response, method);
    }

    /**
     * @param response
     * @param method
     */
    private void afterProcessResponse(JSONObject response, Method<T> method) {
        Method<T> newMethod = null;
        try {
            newMethod = getParamsForNextRequest(response, method);
        } catch (JSONException e) {
            Log.e(TAG, "Cannot get new params, finishing prematurely");
            e.printStackTrace();
        }

        if (newMethod != null) {
            requestQueue.add(buildRequest(newMethod));
        } else {
            onFinish();
        }
    }
}
