package dto.orderapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import dto.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private String orderId;
    private String clientId;
    private double amount;
    private OrderStatus status;
    private String dateCreated;
    private String dateUpdated;
}
