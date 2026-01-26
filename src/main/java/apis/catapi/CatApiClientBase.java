package apis.catapi;

import apis.common.AbstractApiClient;
import okhttp3.Request;


public abstract class CatApiClientBase extends AbstractApiClient {
    protected static final String BASE_URL = "https://api.thecatapi.com/v1";
    protected static final String API_KEY = "API_KEY";

    protected CatApiClientBase() {
        super(BASE_URL);
    }

    protected Request.Builder baseRequestBuilder(String endpoint) {
        return super.baseRequestBuilder(endpoint)
                .addHeader("x-api-key", API_KEY);
    }
}
