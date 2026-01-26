package common.exceptions;

public class OrderApiMappingException extends ApiException {
    public OrderApiMappingException(String message) {
        super(message);
    }

    public OrderApiMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
