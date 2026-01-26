package apis;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingOkHttpInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingOkHttpInterceptor.class);

    @Override
    public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        String requestBodyString = "";
        RequestBody requestBody = request.body();
        if (requestBody != null && requestBody.contentLength() > 0) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestBodyString = buffer.readString(StandardCharsets.UTF_8);
        }

        long t1 = System.nanoTime();
        String method = request.method();
        // Use parameterized logging so we don't build strings unnecessarily
        if (!requestBodyString.isEmpty()) {
            logger.info("\nSending {} request {} on {}\n{}\nRequest body: {}\n",
                    method, request.url(), chain.connection(), request.headers(), requestBodyString);
        } else {
            logger.info("\nSending {} request {} on {}\n{}",
                    method, request.url(), chain.connection(), request.headers());
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        // Use peekBody to read a copy of the response for logging without consuming the real body
        ResponseBody peekedBody = response.peekBody(1024 * 1024);
        String responseBodyString = peekedBody.string() + "\n\n";
        String elapsedMs = String.format("%.1f", (t2 - t1) / 1e6d);
        logger.info("\nReceived response for {} in {}ms\n{}\n{}",
                response.request().url(), elapsedMs, response.headers(), responseBodyString);

        return response;
    }
}
