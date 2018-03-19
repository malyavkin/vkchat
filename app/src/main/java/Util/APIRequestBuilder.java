package Util;

import java.util.ArrayList;
import java.util.List;

import Util.API.Method;


public class APIRequestBuilder {
    private String key;
    public APIRequestBuilder(String key) {
        this.key = key;
    }


    public String makeRequestUrl(Method m) {
        ArrayList<String> queryParams = new ArrayList<>();
        ArrayList<KV> pairs = m.getParams();
        for (KV pair : pairs) {

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
                m.getMethod(),
                params.toString(),
                this.key,
                Constants.API_V);


    }
}

