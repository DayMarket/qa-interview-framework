package uz.daymarket.QaInterviewFramework.orderApi;

import dto.enums.ClientStatus;
import dto.enums.OrderStatus;
import dto.orderapi.Order;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.common.RandomUtils;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.api.OrderApiMappers.mapOrder;

@DisplayName("Order API: Create order")
class CancelCreatedOrderTests extends BaseClientTest {
    private Order createdOrder;

    @BeforeEach
    void createOrder() {
        Number orderAmount = (long) RandomUtils.randomInt(1_000_000);
        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientHelper.getRandomClient(ClientStatus.ENABLED).getClientId());
        createdOrder = mapOrder(createOrderResponse);
    }

    @Test
    @DisplayName("Cancel created order returns 200 and status CREATED")
    void cancelCreatedOrderTests() {
        Order cancelOrderResponse = orderApi.cancelOrder(createdOrder.getOrderId(), 200);

        step("Проверить параметры созданного заказа", () -> {
            assertAll(
                    () -> assertThat(cancelOrderResponse.getOrderId())
                            .as("Expected orderId equals created orderId " + createdOrder.getOrderId())
                            .isEqualTo(createdOrder.getOrderId()),
                    () -> assertThat(cancelOrderResponse.getStatus())
                            .as("Expected created order to have status " + OrderStatus.CREATED)
                            .isEqualTo(OrderStatus.CANCELED)
            );
        });
    }

}
