package Util.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Constants;


public class APIRequestBuilder {
    private final String key;

    public APIRequestBuilder(String key) {
        this.key = key;
    }


    public String makeRequestUrl(Method<?> m) {
        HashMap<String, String> params = m.getParams();
        List<String> queryParams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {

            queryParams.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        StringBuilder queryString = new StringBuilder();
        for (String item : queryParams) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(item);
        }

        return String.format("%s/method/%s?%s&access_token=%s&v=%s",
                Constants.API_DOMAIN,
                m.getMethod(),
                queryString.toString(),
                this.key,
                Constants.API_V);


    }
}

