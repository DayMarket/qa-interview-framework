package apis.theCatApi;

import apis.AllureOkHttpInterceptor;
import apis.LoggingOkHttpInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class BaseCatApi {
    protected static final String BASE_URL = "http://api.thecatapi.com/v1";
    protected static final String API_KEY = "API_KEY";

    protected final OkHttpClient client;

    protected BaseCatApi() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new AllureOkHttpInterceptor())
                .addInterceptor(new LoggingOkHttpInterceptor())
                .build();
    }

    protected Request.Builder baseRequestBuilder(String endpoint) {
        return new Request.Builder()
                .url(BASE_URL + endpoint)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", API_KEY);
    }
}
