import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.RestAssureOrderAndRecipe;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTest {
    CreateUser CreateUserTest = new CreateUser();
    RestAssureOrderAndRecipe RestAssureOrderAndRecipeTest = new RestAssureOrderAndRecipe();

    @Before
    public void setUp() {
        CreateUser.userRegistration();
    }

    @After
    public void tearDown() {
        CreateUser.delete();
    }

    @Test
    @DisplayName("Вход авторизованного пользователя")
    @Description("Логин авторизованного пользователя")
    public void loginUserPozitiveTest() {
        CreateUser.userRegistration();
        CreateUser.userLogIn();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход с неверным логином или паролем")
    @Description("Логин с неверным именем или паролем")
    public void loginWithIncorrectData() {
        CreateUser.userIncorrectDataLOgin();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }
}
