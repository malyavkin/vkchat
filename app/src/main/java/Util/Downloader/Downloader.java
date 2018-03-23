package Util.Downloader;

import android.util.SparseArray;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import Persistence.Entities.Model;
import Util.API.APIRequestBuilder;
import Util.API.Method;
import Util.Listener;

/**
 * Downloader is a class that talks to request queue and decides what to do
 * - when error occurs
 * - when all work is done
 *
 * @param <T>
 */
public class Downloader<T extends Model> {
    protected final RequestQueue requestQueue;
    protected final SparseArray<T> items;
    private final APIRequestBuilder api;
    private final Listener<SparseArray<T>> listener;

    public Downloader(RequestQueue q,
                      APIRequestBuilder api,
                      Listener<SparseArray<T>> listener) {
        this.api = api;
        this.requestQueue = q;
        this.listener = listener;
        this.items = new SparseArray<>();
    }

    void onFinish() {
        listener.call(items);
    }

    private void processError(VolleyError error, Method<T> method) {
        requestQueue.add(buildRequest(method));
    }

    void onResponseHandler(JSONObject response, Method<T> method) {
        processResponse(response, method);
    }

    protected JsonObjectRequest buildRequest(final Method<T> method) {
        String s = api.makeRequestUrl(method);
        return new JsonObjectRequest(s, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onResponseHandler(response, method);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        processError(error, method);
                    }
                }

        );
    }

    protected void processResponse(JSONObject response, Method<T> method) {

    }
}
