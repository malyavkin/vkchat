package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Util.API.Method;


public class APIRequestBuilder {
    private String key;
    public APIRequestBuilder(String key) {
        this.key = key;
    }


    public String makeRequestUrl(Method m) {
        ArrayList<String> queryParams = new ArrayList<>();
        HashMap<String, String> pairs = m.getParams();
        for (Map.Entry<String, String> entry : pairs.entrySet()) {

            queryParams.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
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

