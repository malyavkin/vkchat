package Util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import Util.Service.Service;

public class Startup {
    public Startup(Context ctx) {
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        Service.getInstance().setRequestQueue(requestQueue);
    }
}
