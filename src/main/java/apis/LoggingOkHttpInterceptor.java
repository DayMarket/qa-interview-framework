package apis;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingOkHttpInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingOkHttpInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String requestBodyString = "";
        RequestBody requestBody = request.body();
        if (requestBody != null && requestBody.contentLength() > 0) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestBodyString = buffer.readString(StandardCharsets.UTF_8);
        }

        long t1 = System.nanoTime();
        if (!requestBodyString.isEmpty()) {
            logger.info(String.format("Sending request %s on %s%n%s%nRequest body: %s",
                    request.url(), chain.connection(), request.headers(), requestBodyString));
        } else {
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }

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