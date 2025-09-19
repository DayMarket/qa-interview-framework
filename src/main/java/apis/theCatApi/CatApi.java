package apis.theCatApi;

import io.qameta.allure.Step;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class CatApi extends BaseCatApi {

    @Step("Получить породы кошачьих")
    public Response getAllBreeds() {
        Request request = baseRequestBuilder("/breeds")
                .addHeader("limit", "6")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when getting cat Breeds " + e.getMessage());
        }
    }
}
