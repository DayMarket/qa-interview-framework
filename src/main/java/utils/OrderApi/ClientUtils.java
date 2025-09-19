package utils.OrderApi;

import apis.OrderApi.ClientsApi;
import classes.OrderApi.Client;
import enums.OrderApi.ClientStatus;
import io.qameta.allure.Step;

import java.util.List;

import static utils.OrderApi.OrderApiMappers.mapClients;

public class ClientUtils {

    @Step("Выбрать случайного клиента со статусом {status}")
    public Client getRandomClient(ClientStatus status) {
        List<Client> enabledClients = mapClients(new ClientsApi().getAllClients()).stream()
                .filter(client -> client.getStatus().equals(ClientStatus.enabled)).toList();
        return enabledClients.get((int) (Math.random() * enabledClients.size()));
    }
}
