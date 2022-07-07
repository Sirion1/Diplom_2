import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.RestAssureOrderAndRecipe;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserWithoutTokenOrderTest {

    CreateUser CreateUserTest = new CreateUser();
    RestAssureOrderAndRecipe RestAssureOrderAndRecipeTest = new RestAssureOrderAndRecipe();

    @Test
    @DisplayName("Получение списка заказов НЕзарегестрированного пользователя")
    @Description("Получение списка заказов НЕзарегестрированного пользователя")
    public void getOrdersWithoutAuthorization() {
        RestAssureOrderAndRecipeTest.getOrderRecipeWithoutAuthorization();
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Заказ без авторизации")
    @Description("Заказ неавторизованного пользователя")
    public void makeOrderWithoutTokenTest() {
        CreateUser.userRegistration();
        RestAssureOrderAndRecipeTest.makeOrderWithoutToken();
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false));
    }
}
