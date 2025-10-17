package classes.OrderApi;

import enums.OrderApi.ClientStatus;

public class Client {
    private String clientId;
    private String fullname;
    private ClientStatus status;

    // No-args constructor
    public Client() {
    }

    // All-args constructor
    public Client(String clientId, String fullname, ClientStatus status) {
        this.clientId = clientId;
        this.fullname = fullname;
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", fullname='" + fullname + '\'' +
                ", status=" + status +
                '}';
    }
}