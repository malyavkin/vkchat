package Util.Service;

import com.android.volley.RequestQueue;

public class Service {
    private static final Service ourInstance = new Service();

    private RequestQueue q;
    private NameCache nc;

    private Service() {
    }

    public static Service getInstance() {
        return ourInstance;
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
