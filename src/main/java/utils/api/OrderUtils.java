package utils.api;

import apis.orderapi.OrdersApi;
import dto.orderapi.Order;
import dto.orderapi.OrdersData;
import dto.enums.ClientStatus;
import lombok.experimental.UtilityClass;
import okhttp3.Response;
import utils.clientapi.ClientHelper;
import utils.common.RandomUtils;

import java.util.List;

import static utils.api.OrderApiMappers.mapOrder;

@UtilityClass
public class OrderUtils {

    private static final ClientHelper CLIENT_HELPER = new ClientHelper();
    private static final OrdersApi ORDER_API = new OrdersApi();

    public static List<Order> getNewOrders(OrdersData ordersBefore, OrdersData ordersAfter) {
        return ordersAfter.getItems().stream()
                .filter(order -> !ordersBefore.getItems().contains(order))
                .toList();
    }

    public static List<Order> findOrdersById(OrdersData ordersData, String orderId) {
        return ordersData.getItems().stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .toList();
    }

    public static Order createAndCancelOrder() {
        Number orderAmount = (long) RandomUtils.randomInt(1_000_000);

        Response createOrderResponse = ORDER_API.createOrder(
                orderAmount, CLIENT_HELPER.getRandomClient(ClientStatus.ENABLED).getClientId());
        Order createdOrder = mapOrder(createOrderResponse);

        Response payOrderResponse = ORDER_API.cancelOrder(createdOrder.getOrderId());
        return mapOrder(payOrderResponse);
    }

}
