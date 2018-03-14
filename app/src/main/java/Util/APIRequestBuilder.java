package Util;

import java.util.ArrayList;
import java.util.List;


public class APIRequestBuilder {
    private String key;
    public APIRequestBuilder(String key) {
        this.key = key;
    }


    public String makeRequestUrl(String method, List<KV> parameters) {
        ArrayList<String> queryParams = new ArrayList<>();
        for (KV pair : parameters) {

            queryParams.add(String.format("%s=%s", pair.first, pair.second));
        }

        StringBuilder params = new StringBuilder();
        for(String item: queryParams) {
            if(params.length() > 0) {
                params.append("&");
            }
            params.append(item);
        }

        return String.format("%s/method/%s?%s&access_token=%s&v=%s",
                Constants.API_DOMAIN,
                method,
                params.toString(),
                this.key,
                Constants.API_V);


    }

    public String getDialogs(int offset, int count, boolean unreadOnly) {

        ArrayList<KV> params = new ArrayList<>();
        params.add(new KV("offset", offset));
        params.add(new KV("count", count));
        if(unreadOnly) {
            params.add(new KV("unread", "1"));
        }

        return makeRequestUrl("messages.getDialogs", params);


    }
}

