package common.exceptions;

public class ClientNotFoundException extends ApiException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
