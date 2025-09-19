package utils.OrderApi;

import classes.OrderApi.Client;
import classes.OrderApi.OrdersData;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class OrderApiMappers {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Client> mapClients(Response response) {
        try (response) {
            return objectMapper.readValue(
                    response.body().string(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Client.class)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to map List<Client> from response", e);
        }
    }

    public static OrdersData mapOrdersData(Response response) {
        try (response) {
            return objectMapper.readValue(response.body().string(), OrdersData.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map OrdersData from response", e);
        }
    }
}
