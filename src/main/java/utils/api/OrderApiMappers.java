package utils.api;

import common.exceptions.OrderApiMappingException;
import dto.orderapi.Client;
import dto.orderapi.Order;
import dto.orderapi.OrderApiError;
import dto.orderapi.OrdersData;
import okhttp3.Response;
import lombok.experimental.UtilityClass;
import utils.common.JsonUtils;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class OrderApiMappers {

    public static List<Client> mapClients(Response response) {
        if (response == null) {
            throw new OrderApiMappingException("response must not be null");
        }

        try (response) {
            if (!response.isSuccessful()) {
                String errorBody = "";
                errorBody = response.body().string();
                throw new OrderApiMappingException(
                        "Failed to get clients. HTTP " + response.code() + " " + response.message()
                                + (errorBody.isBlank() ? "" : (", body: " + errorBody))
                );
            }

            String json = response.body().string();
            if (json.isBlank()) {
                return List.of();
            }

            return JsonUtils.readValue(
                    json,
                    JsonUtils.constructCollectionType(List.class, Client.class)
            );
        } catch (IOException e) {
            throw new OrderApiMappingException("Failed to map List<Client> from response", e);
        }
    }

    public static OrdersData mapOrdersData(Response response) {
        try (response) {
            return JsonUtils.readValue(response.body().string(), OrdersData.class);
        } catch (IOException e) {
            throw new OrderApiMappingException("Failed to map OrdersData from response", e);
        }
    }

    public static Order mapOrder(Response response) {
        try (response) {
            return JsonUtils.readValue(response.body().string(), Order.class);
        } catch (IOException e) {
            throw new OrderApiMappingException("Failed to map Order from response", e);
        }
    }

    public static OrderApiError mapError(Response response) {
        try (response) {
            return JsonUtils.readValue(response.body().string(), OrderApiError.class);
        } catch (IOException e) {
            throw new OrderApiMappingException("Failed to map OrderApiError from response", e);
        }
    }
}
