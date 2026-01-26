package utils.clientapi;

import apis.orderapi.ClientsApi;
import common.exceptions.ClientNotFoundException;
import dto.orderapi.Client;
import dto.enums.ClientStatus;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.SneakyThrows;
import utils.common.JsonUtils;
import utils.common.RandomUtils;

import static utils.api.OrderApiMappers.mapClients;

public class ClientHelper {

    private final ClientsApi clientsApi;
    public ClientHelper(ClientsApi clientsApi) {
        this.clientsApi = Objects.requireNonNull(clientsApi, "clientsApi must not be null");
    }

    public ClientHelper() {
        this(new ClientsApi());
    }

    @SneakyThrows
    @Step("Выбрать случайного клиента со статусом {status}")
    public Client getRandomClient(ClientStatus status) {
        Objects.requireNonNull(status, "status must not be null");

        List<Client> allClients = Optional.ofNullable(mapClients(clientsApi.getAllClients()))
                .orElse(List.of());

        List<Client> matchingClients = allClients.stream()
                .filter(Objects::nonNull)
                .filter(client -> client.getStatus() == status)
                .toList();

        if (matchingClients.isEmpty()) {
            throw new ClientNotFoundException("Не найдено клиентов со статусом: " + status);
        }

        Client rndClient = RandomUtils.randomElementFromList(matchingClients);
        Allure.addAttachment("Выбран случайный клиент", "application/json", JsonUtils.writeValueAsString(rndClient));

        return rndClient;
    }
}
