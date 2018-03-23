package Util.Network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Queue {
    private static final Queue ourInstance = new Queue();

    private RequestQueue q;

    private Queue() {
    }

    public static Queue getInstance() {
        return ourInstance;
    }

    public RequestQueue getQueue() {
        return q;
    }

    public void setQueue(Context ctx) {
        q = Volley.newRequestQueue(ctx);
    }
}
