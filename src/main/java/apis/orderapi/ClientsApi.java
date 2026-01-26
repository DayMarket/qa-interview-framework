package apis.orderapi;

import common.exceptions.ClientsApiException;
import io.qameta.allure.Step;
import okhttp3.Request;
import okhttp3.Response;

public class ClientsApi extends OrderApiClientBase {

    @Step("Получить список всех клиентов")
    public Response getAllClients() {
        return get("/clients", "getting clients list");
    }

    private Response get(String path, String action) {
        Request request = buildGetRequest(path);
        return execute(request, action, (act, ex) -> new ClientsApiException("Got an error when " + act, ex));
    }
}
