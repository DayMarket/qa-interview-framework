package apis.OrderApi;

import io.qameta.allure.Step;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ClientsApi extends BaseOrderApi {

    @Step("Получить список клиентов")
    public Response getAllClients() {
        Request request = baseRequestBuilder("/clients")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when getting clients " + e.getMessage());
        }
    }

}
