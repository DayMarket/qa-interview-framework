package apis;

import io.qameta.allure.Allure;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectWriter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Allure interceptor logger for OkHttp.
 */
public class AllureOkHttpInterceptor implements Interceptor {

    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    public AllureOkHttpInterceptor setRequestTemplate(final String templatePath) {
        this.requestTemplatePath = templatePath;
        return this;
    }

    public AllureOkHttpInterceptor setResponseTemplate(final String templatePath) {
        this.responseTemplatePath = templatePath;
        return this;
    }

    private static String beautifyBody(String body, String contentType) {
        if (body == null || body.isEmpty()) {
            return body;
        }
        try {
            if (contentType != null && contentType.contains("application/json")) {
                ObjectMapper mapper = new ObjectMapper();
                Object json = mapper.readValue(body, Object.class);
                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                return writer.writeValueAsString(json);
            } else if (contentType != null && (contentType.contains("application/xml") || contentType.contains("text/xml"))) {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamSource source = new StreamSource(new StringReader(body));
                StringWriter out = new StringWriter();
                transformer.transform(source, new StreamResult(out));
                return out.toString();
            }
        } catch (Exception ignored) {
            // Fallback to raw body if beautification fails
        }
        return body;
    }


    @Override
    public Response intercept(final Chain chain) throws IOException {
        final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();

        final Request request = chain.request();
        final String requestUrl = request.url().toString();

        // Request headers attachment
        final HttpRequestAttachment.Builder requestHeadersBuilder = HttpRequestAttachment.Builder
                .create("Request Data", requestUrl)
                .setMethod(request.method())
                .setHeaders(toMapConverter(request.headers().toMultimap()));
        processor.addAttachment(requestHeadersBuilder.build(), new FreemarkerAttachmentRenderer(requestTemplatePath));

        // Request body attachment
        final RequestBody requestBody = request.body();
        if (Objects.nonNull(requestBody)) {
            String rawBody = readRequestBody(requestBody);
            String contentType = requestBody.contentType() != null ? requestBody.contentType().toString() : null;
            String beautified = beautifyBody(rawBody, contentType);
            InputStream inputStream = new ByteArrayInputStream(beautified.getBytes(StandardCharsets.UTF_8));
            Allure.addAttachment("Request Body", "application/json", inputStream, ".json");
        }

        final Response response = chain.proceed(request);

        // Response headers attachment
        final HttpResponseAttachment.Builder responseHeadersBuilder = HttpResponseAttachment.Builder
                .create("Response Data")
                .setResponseCode(response.code())
                .setHeaders(toMapConverter(response.headers().toMultimap()));
        processor.addAttachment(responseHeadersBuilder.build(), new FreemarkerAttachmentRenderer(responseTemplatePath));

        // Response body attachment
        final Response.Builder responseBuilder = response.newBuilder();
        final ResponseBody responseBody = response.body();
        if (Objects.nonNull(responseBody)) {
            final byte[] bytes = responseBody.bytes();
            String rawBody = new String(bytes, StandardCharsets.UTF_8);
            String contentType = responseBody.contentType() != null ? responseBody.contentType().toString() : null;
            String beautified = beautifyBody(rawBody, contentType);
            InputStream inputStream = new ByteArrayInputStream(beautified.getBytes(StandardCharsets.UTF_8));
            Allure.addAttachment("Response Body", "application/json", inputStream, ".json");
            responseBuilder.body(ResponseBody.create(responseBody.contentType(), bytes));
        }

        return responseBuilder.build();
    }


    private static Map<String, String> toMapConverter(final Map<String, List<String>> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach((key, value) -> result.put(key, String.join("; ", value)));
        return result;
    }

    private static String readRequestBody(final RequestBody requestBody) throws IOException {
        try (Buffer buffer = new Buffer()) {
            requestBody.writeTo(buffer);
            return buffer.readString(StandardCharsets.UTF_8);
        }
    }
}
