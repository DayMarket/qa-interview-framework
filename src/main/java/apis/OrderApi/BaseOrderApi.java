package apis.OrderApi;

import apis.AllureOkHttpInterceptor;
import apis.LoggingOkHttpInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Base class for Order API clients.
 * <p>
 * Service Swagger documentation:
 * <a href="https://qa-interview-service.dev.cluster.daymarket.uz/swagger/index.html#">
 * https://qa-interview-service.dev.cluster.daymarket.uz/swagger/index.html#
 * </a>
 */

public class BaseOrderApi {
    protected static final String BASE_URL = "https://qa-interview-service.dev.cluster.daymarket.uz";

    protected final OkHttpClient client;

    protected BaseOrderApi() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new AllureOkHttpInterceptor())
                .addInterceptor(new LoggingOkHttpInterceptor())
                .build();
    }

    protected Request.Builder baseRequestBuilder(String endpoint) {
        return new Request.Builder()
                .url(BASE_URL + endpoint)
                .addHeader("Content-Type", "application/json");
    }
}
