package uz.daymarket.QaInterviewFramework.catApi;

import apis.catapi.CatApi;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uz.daymarket.QaInterviewFramework.BaseTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Cat API: Breeds")
class GetAllBreedsTests extends BaseTest {

    @Test
    @DisplayName("Get all breeds returns 200")
    void getAllBreedsTest() {
        CatApi api = new CatApi();
        Response response = api.getAllBreeds();
        assertThat(response.code())
                .as("Check that status code is 200")
                .isEqualTo(200);

    }
}
