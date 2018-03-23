package Util;

import android.content.Context;

import Util.Network.Queue;

public class Startup {
    public Startup(Context ctx) {
        Queue.getInstance().setQueue(ctx);
    }
}
