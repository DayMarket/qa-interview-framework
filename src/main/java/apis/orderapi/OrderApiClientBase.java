package apis.orderapi;

import apis.common.AbstractApiClient;
/**
 * Base class for Order API clients.
 * <p>
 * Service Swagger documentation:
 * <a href="https://qa-interview-service.dev.cluster.daymarket.uz/swagger/index.html#">
 * https://qa-interview-service.dev.cluster.daymarket.uz/swagger/index.html#
 * </a>
 */

public class OrderApiClientBase extends AbstractApiClient {
    protected static final String BASE_URL = "https://qa-interview-service.dev.cluster.daymarket.uz";

    protected OrderApiClientBase() {
        super(BASE_URL);
    }
}
