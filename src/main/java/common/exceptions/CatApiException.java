package common.exceptions;

public class CatApiException extends ApiException {
    public CatApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
