package utils.OrderApi;

import classes.OrderApi.Order;
import classes.OrderApi.OrdersData;

import java.util.List;

public class OrderUtils {

    public List<Order> getNewOrders(OrdersData ordersBefore, OrdersData ordersAfter) {
        return ordersAfter.getItems().stream()
                .filter(order -> !ordersBefore.getItems().contains(order))
                .toList();
    }

}
