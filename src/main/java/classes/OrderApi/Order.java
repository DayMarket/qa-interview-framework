package classes.OrderApi;

import enums.OrderApi.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String clientId;
    private double amount;
    private OrderStatus status;
    private String dateCreated;
    private String dateUpdated;
}
