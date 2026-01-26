package apis.catapi;

import common.exceptions.CatApiException;
import io.qameta.allure.Step;
import okhttp3.Request;
import okhttp3.Response;

public class CatApi extends CatApiClientBase {

    @Step("Получить все породы кошачьих")
    public Response getAllBreeds() {
        return get("/breeds", "getting cat breeds");
    }

    private Response get(String path, String action) {
        Request request = buildGetRequest(path);
        return execute(request, action, (act, ex) -> new CatApiException("Got an error when " + act, ex));
    }
}
