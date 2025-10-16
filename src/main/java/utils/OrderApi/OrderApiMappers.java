package utils.OrderApi;

import classes.OrderApi.Client;
import classes.OrderApi.OrderApiError;
import classes.OrderApi.OrdersData;
import classes.OrderApi.Order;
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

    public static Order mapOrder(Response response) {
        try (response) {
            return objectMapper.readValue(response.body().string(), Order.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map Order from response", e);
        }
    }

    public static OrderApiError mapError(Response response) {
        try (response) {
            return objectMapper.readValue(response.body().string(), OrderApiError.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to map Order from response", e);
        }
    }
}
