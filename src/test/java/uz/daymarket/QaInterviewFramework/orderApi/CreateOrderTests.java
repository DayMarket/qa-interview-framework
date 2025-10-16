package uz.daymarket.QaInterviewFramework.orderApi;

import apis.OrderApi.OrdersApi;
import classes.OrderApi.Order;
import enums.OrderApi.ClientStatus;
import enums.OrderApi.OrderStatus;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.OrderApi.ClientUtils;
import utils.OrderApi.OrderUtils;
import uz.daymarket.QaInterviewFramework.BaseTest;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.OrderApi.OrderApiMappers.mapOrder;
import static utils.OrderApi.OrderApiMappers.mapOrdersData;

public class CreateOrderTests extends BaseTest {

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

        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientUtils.getRandomClient(ClientStatus.enabled).getClientId());

        Order createdOrder = mapOrder(createOrderResponse);

        List<Order> foundOrders = orderUtils.findOrdersById(
                mapOrdersData(orderApi.getAllOrders()),
                createdOrder.getOrderId()
        );

        step("Проверить параметры созданного заказа", () -> {
            assertAll(
                    () -> assertThat(createOrderResponse.code())
                            .as("Expected HTTP status 201 when creating an order")
                            .isEqualTo(201),
                    () -> assertThat(createdOrder.getStatus())
                            .as("Expected created order to have status " + OrderStatus.Created)
                            .isEqualTo(OrderStatus.Created)
                    // place for more assertions
            );
        });
    }

}
