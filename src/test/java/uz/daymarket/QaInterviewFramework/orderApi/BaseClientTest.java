package uz.daymarket.QaInterviewFramework.orderApi;

import apis.orderapi.OrdersApi;
import dto.enums.ClientStatus;
import dto.enums.OrderStatus;
import dto.orderapi.Order;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.clientapi.ClientHelper;
import utils.common.RandomUtils;
import uz.daymarket.QaInterviewFramework.BaseTest;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.api.OrderApiMappers.mapOrder;

@DisplayName("Order API: Create order")
public abstract class BaseClientTest extends BaseTest {

    protected ClientHelper clientHelper;
    protected OrdersApi orderApi;

    @BeforeEach
    void setUp() {
        clientHelper = new ClientHelper();
        orderApi = new OrdersApi();
    }

}
