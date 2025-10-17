package classes.OrderApi;

public class OrderApiError {
    private String error;

    // No-args constructor
    public OrderApiError() {
    }

    // All-args constructor
    public OrderApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "OrderApiError{" +
                "error='" + error + '\'' +
                '}';
    }
}
