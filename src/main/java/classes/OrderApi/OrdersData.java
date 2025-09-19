package classes.OrderApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersData {
    private List<Order> items;
    private int page;
    private int pageSize;
    private int total;
    private int pages;
}
