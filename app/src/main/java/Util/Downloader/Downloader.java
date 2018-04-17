package Util.Downloader;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import Persistence.Entities.Model;
import Util.API.Method;
import Util.Listener;
import Util.Service.Service;

/**
 * Downloader is a class that talks to request queue and decides what to do
 * - when error occurs
 * - when all work is done
 *
 * @param <T>
 */
public abstract class Downloader<T extends Model> {
    protected final RequestQueue requestQueue;
    protected final HashMap<String, T> items;
    protected final Listener<HashMap<String, T>> listener;

    public Downloader(Listener<HashMap<String, T>> listener) {
        this.requestQueue = Service.getInstance().getRequestQueue();
        this.listener = listener;
        this.items = new HashMap<>();
    }

    void onFinish() {
        listener.call(items);
    }

    private void processError(VolleyError error, Method<T> method) {
        requestQueue.add(buildRequest(method));
    }

    void onResponseHandler(JSONObject response, Method<T> method) {
        processResponse(response, method);
        onFinish();
    }

    protected JsonObjectRequest buildRequest(final Method<T> method) {
        String s = Service.getInstance().getApi().makeRequestUrl(method);
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

    protected abstract void processResponse(JSONObject response, Method<T> method);

    /**
     * get initial parameters
     *
     * @return set of parameters for the first request
     */
    protected abstract Method<T> getInitialParams();


    protected void run() {
        requestQueue.add(buildRequest(getInitialParams()));
    }
}
