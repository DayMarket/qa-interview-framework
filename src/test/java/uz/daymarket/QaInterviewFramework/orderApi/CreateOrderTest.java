package uz.daymarket.QaInterviewFramework.orderApi;

import apis.OrderApi.OrdersApi;
import classes.OrderApi.Order;
import classes.OrderApi.OrdersData;
import enums.OrderApi.ClientStatus;
import enums.OrderApi.OrderStatus;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.OrderApi.ClientUtils;
import utils.OrderApi.OrderUtils;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

        OrdersData ordersAfter = mapOrdersData(orderApi.getAllOrders());

        List<Order> newOrders = orderUtils.getNewOrders(ordersBefore, ordersAfter);

        Order CreatedOrder = newOrders.getFirst();

        step("Проверить параметры созданного заказа", () -> {
            assertAll(
                    () -> assertThat(createOrderResponse.code()).isEqualTo(404),  // ¯_(ツ)/¯
                    () -> assertThat(CreatedOrder.getAmount())
                            .as("Check that created order amount is")
                            .isEqualTo(orderAmount.doubleValue()),
                    () -> assertThat(newOrders.size()).isEqualTo(1),
                    () -> assertThat(CreatedOrder.getStatus()).isEqualTo(OrderStatus.Created),
                    () -> assertThat(ordersAfter.getTotal()).isEqualTo(ordersBefore.getTotal() + 1)
            );
        });
    }

}
