package Util.Downloader;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Model;
import Util.API.Method;
import Util.Listener;

/**
 * SequentialDownloader is a class used to download portions of data sequentially
 * @param <T>
 */
public abstract class SequentialDownloader<T extends Model> extends Downloader<T> {
    private final static String TAG = "SequentialDownloader";
    private Method<T> newMethod = null;

    protected SequentialDownloader(Listener<HashMap<String, T>> onFinishListener) {
        super(onFinishListener);
    }

    /**
     * @param response current response
     * @param method   current set of parameters
     * @return set of parameters for the next request given current parameters and response
     * @throws JSONException {response} is JSONObject, so there's that
     */
    protected abstract Method<T> getParamsForNextRequest(JSONObject response, Method<T> method) throws JSONException;

    @Override
    void onResponseHandler(JSONObject response, Method<T> method) {
        processResponse(response, method);
        afterProcessResponse(response, method);
    }

    protected void loadMore() {
        requestQueue.add(buildRequest(newMethod));
    }

    protected void onPartialFinish() {

    }

    /**
     * @param response
     * @param method
     */
    private void afterProcessResponse(JSONObject response, Method<T> method) {

        try {
            newMethod = getParamsForNextRequest(response, method);
        } catch (JSONException e) {
            Log.e(TAG, "Cannot get new params, finishing prematurely");
            e.printStackTrace();
        }

        onPartialFinish();
        if (newMethod == null) {
            onFinish();
        }
    }
}
