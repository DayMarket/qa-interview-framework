package common.exceptions;

public class OrdersApiException extends ApiException {
    public OrdersApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
