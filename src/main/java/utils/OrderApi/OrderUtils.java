package utils.OrderApi;

import apis.OrderApi.OrdersApi;
import classes.OrderApi.Order;
import classes.OrderApi.OrdersData;
import enums.OrderApi.ClientStatus;
import okhttp3.Response;

import java.util.List;

import static utils.OrderApi.OrderApiMappers.mapOrder;

public class OrderUtils {

    private final ClientUtils clientUtils = new ClientUtils();
    private final OrdersApi orderApi = new OrdersApi();

    public List<Order> getNewOrders(OrdersData ordersBefore, OrdersData ordersAfter) {
        return ordersAfter.getItems().stream()
                .filter(order -> !ordersBefore.getItems().contains(order))
                .toList();
    }

    public List<Order> findOrdersById(OrdersData ordersData, String orderId) {
        return ordersData.getItems().stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .toList();
    }

    public Order createAndCancelOrder() {
        Number orderAmount = (long) (Math.random() * 1_000_000);

        Response createOrderResponse = orderApi.createOrder(
                orderAmount, clientUtils.getRandomClient(ClientStatus.enabled).getClientId());
        Order createdOrder = mapOrder(createOrderResponse);

        Response payOrderResponse = orderApi.cancelOrder(createdOrder.getOrderId());
        return mapOrder(payOrderResponse);
    }

}
