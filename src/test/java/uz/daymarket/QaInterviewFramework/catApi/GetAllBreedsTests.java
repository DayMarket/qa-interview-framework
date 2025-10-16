package uz.daymarket.QaInterviewFramework.catApi;

import apis.theCatApi.CatApi;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import uz.daymarket.QaInterviewFramework.BaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllBreedsTests extends BaseTest {

    @Test
    public void getAllBreedsTest() {
        CatApi api = new CatApi();
        Response response = api.getAllBreeds();
        assertThat(response.code())
                .as("Check that status code is 200")
                .isEqualTo(200);

    }

}
