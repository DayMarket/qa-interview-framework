package classes.OrderApi;

import enums.OrderApi.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private String clientId;
    private String fullname;
    private ClientStatus status;
}