package Util.Service;

import com.android.volley.RequestQueue;

import Util.API.APIRequestBuilder;

public class Service {
    private static final Service ourInstance = new Service();

    private RequestQueue q;
    private NameCache nc;
    private APIRequestBuilder api;

    private Service() {
    }

    public static Service getInstance() {
        return ourInstance;
    }

    public APIRequestBuilder getApi() {
        return api;
    }

    public void setApi(APIRequestBuilder api) {
        this.api = api;
    }

    public RequestQueue getRequestQueue() {
        return q;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        q = requestQueue;
    }

    public NameCache getNameCache() {
        return nc;
    }

    public void setNameCache(NameCache nc) {
        this.nc = nc;
    }


}
