package uz.daymarket.QaInterviewFramework.orderApi;

import apis.OrderApi.OrdersApi;
import classes.OrderApi.Order;
import classes.OrderApi.OrdersData;
import enums.OrderApi.ClientStatus;
import enums.OrderApi.OrderStatus;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.OrderApi.ClientUtils;
import utils.OrderApi.OrderUtils;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.OrderApi.OrderApiMappers.mapOrder;
import static utils.OrderApi.OrderApiMappers.mapOrdersData;

public class CreateOrderTest {

    private ClientUtils clientUtils;
    private OrderUtils orderUtils;
    private OrdersApi orderApi;

    @BeforeEach
    public void setUp() {
        clientUtils = new ClientUtils();
        orderApi = new OrdersApi();
        orderUtils = new OrderUtils();
    }

    @Test
    public void createOrderTest() {
        Number orderAmount = (long) (Math.random() * 1_000_000);

        OrdersData ordersBefore = mapOrdersData(orderApi.getAllOrders());

        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientUtils.getRandomClient(ClientStatus.enabled).getClientId());

        Order createdOrder = mapOrder(createOrderResponse);

        List<Order> foundOrders = mapOrdersData(orderApi.getAllOrders())
                .getItems().stream()
                .filter(order -> order.getOrderId().equals(createdOrder.getOrderId()))
                .toList();

        step("Проверить параметры созданного заказа", () -> {
            assertAll(
                    () -> assertThat(createOrderResponse.code()).isEqualTo(201),
                    () -> assertThat(createdOrder.getAmount())
                            .as("Check that created order amount is")
                            .isEqualTo(orderAmount.doubleValue()),
                    () -> assertThat(createdOrder.getStatus()).isEqualTo(OrderStatus.Created),
                    () -> assertThat(foundOrders.size()).isEqualTo(1),
                    () -> assertThat(foundOrders.getFirst().getAmount()).isEqualTo(orderAmount.doubleValue())
            );
        });
    }

}
