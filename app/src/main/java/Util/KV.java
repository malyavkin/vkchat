package Util;

import android.support.v4.util.Pair;

public class KV extends Pair<String, String> {

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public KV(String first, String second) {
        super(first, second);
    }

    public KV(String first, int second) {
        super(first, String.valueOf(second));
    }
}