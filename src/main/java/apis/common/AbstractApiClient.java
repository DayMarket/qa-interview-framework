package apis.common;

import apis.AllureOkHttpInterceptor;
import apis.LoggingOkHttpInterceptor;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiFunction;

public class AbstractApiClient {
    protected final OkHttpClient client;
    private final String baseUrl;


    public AbstractApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new AllureOkHttpInterceptor())
                .addInterceptor(new LoggingOkHttpInterceptor())
                .build();
    }

    protected Request.Builder baseRequestBuilder(String endpoint) {
        return new Request.Builder()
                .url(buildUrl(endpoint, null))
                .addHeader("Content-Type", "application/json");
    }

    protected Request.Builder baseRequestBuilder(String endpoint, Map<String, String> headers) {
        return new Request.Builder()
                .url(buildUrl(endpoint, null))
                .headers(Headers.of(headers));
    }

    protected Request baseRequest(String endpoint) {
        return baseRequestBuilder(endpoint).build();
    }

    protected Request baseRequest(String endpoint, Map<String, String> headers) {
        return baseRequestBuilder(endpoint, headers).build();
    }

    protected Request buildGetRequest(String endpoint) {
        return baseRequestBuilder(endpoint).get().build();
    }

    protected Request buildGetRequest(String endpoint, Map<String, String> queryParams) {
        return new Request.Builder()
                .url(buildUrl(endpoint, queryParams))
                .addHeader("Content-Type", "application/json")
                .get()
                .build();
    }

    protected Request buildPostRequest(String endpoint, RequestBody body) {
        return baseRequestBuilder(endpoint).post(body).build();
    }

    protected Response execute(
            Request request,
            String action,
            BiFunction<String, IOException, ? extends RuntimeException> exceptionFactory
    ) {
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw exceptionFactory.apply(action, e);
        }
    }

    private HttpUrl buildUrl(String endpoint, Map<String, String> queryParams) {
        String rawUrl = baseUrl + endpoint;
        HttpUrl base = HttpUrl.parse(rawUrl);
        if (base == null) {
            throw new IllegalArgumentException("Invalid URL: " + rawUrl);
        }
        HttpUrl.Builder builder = base.newBuilder();
        if (queryParams != null) {
            queryParams.forEach(builder::addQueryParameter);
        }
        return builder.build();
    }
}
