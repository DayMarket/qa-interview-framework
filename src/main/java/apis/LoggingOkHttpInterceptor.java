package apis;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingOkHttpInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingOkHttpInterceptor.class);

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        logger.info(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        ResponseBody responseBody = response.body();
        String responseBodyString = responseBody != null ? responseBody.string() : "";
        logger.info(String.format("Received response for %s in %.1fms%n%s%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(), responseBodyString));

        // Re-create the response body to return
        Response newResponse = response.newBuilder()
                .body(ResponseBody.create(responseBody != null ? responseBody.contentType() : null, responseBodyString.getBytes()))
                .build();

        return newResponse;
    }
}