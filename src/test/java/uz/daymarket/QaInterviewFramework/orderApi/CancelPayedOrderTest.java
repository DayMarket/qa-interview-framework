package uz.daymarket.QaInterviewFramework.orderApi;

import dto.enums.ClientStatus;
import dto.orderapi.Order;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.api.OrderApiMappers;
import utils.common.RandomUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.api.OrderApiMappers.mapOrder;

class CancelPayedOrderTest extends BaseClientTest {
    private Order createdOrder;

    @BeforeEach
    void createOrder() {
        Number orderAmount = (long) RandomUtils.randomInt(1_000_000);
        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientHelper.getRandomClient(ClientStatus.ENABLED).getClientId());
        createdOrder = mapOrder(createOrderResponse);
    }

    @Test
    @DisplayName("Cancel payed order is forbidden and returns 400")
    @Tag("Negative")
    void cancelPayedOrderTest() {
        Response payOrderResponse = orderApi.payOrder(createdOrder.getOrderId());
        assertThat(payOrderResponse.code())
                .as("Expected HTTP status is 200")
                .isEqualTo(200);
        Order payedOrder = OrderApiMappers.mapOrder(payOrderResponse);

        Response cancelPayedOrderResponse = orderApi.cancelOrder(payedOrder.getOrderId());
        assertThat(cancelPayedOrderResponse.code())
                .as("Expected HTTP status 400 when creating an order")
                .isEqualTo(400);
    }

}
