package uz.daymarket.QaInterviewFramework.orderApi;

import dto.enums.ClientStatus;
import dto.orderapi.Order;
import dto.orderapi.OrderApiError;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.api.OrderApiMappers;
import utils.common.RandomUtils;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.api.OrderApiMappers.mapOrder;

@DisplayName("Order API: Create order")
class PayingCanceledOrderTest extends BaseClientTest {
    private Order createdOrder;

    @BeforeEach
    void createOrder() {
        Number orderAmount = (long) RandomUtils.randomInt(1_000_000);
        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientHelper.getRandomClient(ClientStatus.ENABLED).getClientId());
        createdOrder = mapOrder(createOrderResponse);
    }

    @Test
    @DisplayName("Paying canceled order is forbidden and returns 400")
    @Tag("Negative")
    void payingCanceledOrderTest() {
        Order cancelOrderResponse = orderApi.cancelOrder(createdOrder.getOrderId(), 200);

        Response payOrderResponse = orderApi.payOrder(cancelOrderResponse.getOrderId());
        OrderApiError payOrder = OrderApiMappers.mapError(payOrderResponse);

        step("Проверить параметры созданного заказа", () -> {
            assertAll(
                    () -> assertThat(payOrderResponse.code())
                            .as("Expected HTTP status 400 when creating an order")
                            .isEqualTo(400),
                    () -> assertThat(payOrder.getError())
                            .as("Error message is correct")
                            .isEqualTo("only Created orders can be paid")
            );
        });
    }

}
