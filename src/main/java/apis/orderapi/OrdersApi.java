package apis.orderapi;

import dto.orderapi.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.exceptions.OrdersApiException;
import io.qameta.allure.Step;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.api.OrderApiMappers;
import utils.common.JsonUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrdersApi extends OrderApiClientBase {


    @Step("Получить список всех заказов")
    public Response getAllOrders() {
        Request request = buildGetRequest("/orders");
        return execute(request, "getting orders list",
                (act, ex) -> new OrdersApiException("Got an error when " + act, ex));
    }

    @Step("Создать заказ")
    public Response createOrder(Number amount, String clientId) {
        Order order = Order.builder()
                .amount(amount.doubleValue())
                .clientId(clientId)
                .build();
        String json;
        try {
            json = JsonUtils.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new OrdersApiException("Failed to serialize order request", e);
        }

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = buildPostRequest("/orders", body);
        return execute(request, "creating order",
                (act, ex) -> new OrdersApiException("Got an error when " + act, ex));
    }

    @Step("Отменить заказ {orderId}")
    public Order cancelOrder(String orderId, Integer... expectedCodes) {
        Response cancelOrder = postCancelOrder(orderId);
        List<Integer> expectedCodesList = Arrays.asList(expectedCodes);
         assertThat(cancelOrder.code())
                            .as("Expected HTTP status in list {}".formatted(expectedCodesList))
                            .isIn(expectedCodesList);
        return OrderApiMappers.mapOrder(cancelOrder);
    }

    @Step("Отменить заказ {orderId}")
    public Response cancelOrder(String orderId) {
        return postCancelOrder(orderId);
    }

    private Response postCancelOrder(String orderId) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        Request request = buildPostRequest("/orders/" + orderId + "/cancel", emptyBody);
        return execute(request, "canceling order",
                (act, ex) -> new OrdersApiException("Got an error when " + act, ex));
    }



    @Step("Оплатить заказ {orderId}")
    public Response payOrder(String orderId) {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);
        Request request = buildPostRequest("/orders/" + orderId + "/pay", emptyBody);
        return execute(request, "canceling order",
                (act, ex) -> new OrdersApiException("Got an error when " + act, ex));
    }
}
