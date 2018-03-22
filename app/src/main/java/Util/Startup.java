package Util;

import android.content.Context;

import Util.Network.Queue;

/**
 * Created by amalyavkin on 23/03/2018.
 */

public class Startup {
    public Startup(Context ctx) {
        Queue.getInstance().setQueue(ctx);
    }
}
