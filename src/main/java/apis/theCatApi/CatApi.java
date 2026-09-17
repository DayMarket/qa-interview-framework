package apis.theCatApi;

import io.qameta.allure.Step;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class CatApi extends BaseCatApi {

    @Step("Получить породы кошачьих")
    public Response getImages() {
        HttpUrl url = HttpUrl.Companion.get(BASE_URL + "/images/search")
                .newBuilder()
                .addQueryParameter("limit", "10")
                .addQueryParameter("page", "0")
                .build();
        Request request = baseRequestBuilder("/images/search")
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when getting cat Breeds " + e.getMessage());
        }
    }
}
