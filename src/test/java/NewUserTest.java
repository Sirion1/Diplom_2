import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.RestAssureUser;
import praktikum.user.UserGetter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class NewUserTest {
    CreateUser CreateUserTest = new CreateUser();
    UserGetter UserGetterTest = new UserGetter();

    @Before
    public void setUp() {
        CreateUser.userRegistration();
    }

    @After
    public void tearDown() {
        CreateUserTest.delete();
    }

    @Test
    @DisplayName("Регистрация нового юзера")
    @Description("Регистрация нового юзера")
    public void userRegisterTest() {
        CreateUser.userRegistration();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Регистрация юзера с такими же данными")
    @Description("Регистрация юзеров с одинаковыми данными")
    public void doubleUserRegisterTest() {
        UserGetter.User user = new UserGetter.User();
        CreateUser.userRegistrationWithDobleData();
        CreateUser.userRegistrationWithDobleData();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Регистрация без емейла")
    @Description("Регистрация юезра без емейла")
    public void userRegisterWithoutEmail() {
        Map<String, String> dataMap = new HashMap<>();
        CreateUser.userRegistrationWithoutEmail();
        CreateUserTest.setResponse(given()
                .spec(RestAssureUser.getRestAssureUser())
                .body(dataMap)
                .when()
                .post("auth/register"));
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

}
