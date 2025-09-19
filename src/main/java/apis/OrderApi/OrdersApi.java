package apis.OrderApi;

import io.qameta.allure.Step;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class OrdersApi extends BaseOrderApi {

    @Step("Получить список заказов")
    public Response getAllOrders() {
        Request request = baseRequestBuilder("/orders").build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when getting orders " + e.getMessage());
        }
    }

    @Step("Создать заказ")
    public Response createOrder(Number amount, String clientId) {
        RequestBody body = RequestBody.create(
                "{\"amount\":" + amount + ",\"clientId\":\"" + clientId + "\"}",
                MediaType.parse("application/json")
        );

        Request request = baseRequestBuilder("/orders")
                .post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when creating order " + e.getMessage());
        }
    }

    @Step("Отменить заказ {orderId}")
    public Response cancelOrder(String orderId) {
        Request request = baseRequestBuilder("/orders/" + orderId + "/cancel")
                .post(RequestBody.create(new byte[0], null))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Got an error when canceling order " + e.getMessage());
        }
    }

}