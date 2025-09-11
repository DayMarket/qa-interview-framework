package uz.daymarket.QaInterviewFramework.catApi;

import apis.theCatApi.CatApi;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllBreedsTest {

    @Test
    public void getAllBreedsTest() {
        CatApi api = new CatApi();
        Response response = api.getAllBreeds();
        assertThat(response.code())
                .as("Check that status code is 200")
                .isEqualTo(200);

    }

}
