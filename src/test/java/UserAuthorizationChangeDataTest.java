import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.UserGetter;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserAuthorizationChangeDataTest {
    CreateUser CreateUserTest = new CreateUser();
    UserGetter UserGetterTest = new UserGetter();


    @Before
    public void setUp() {
        CreateUser.userRegistration();
    }

    @After
    public void tearDown() {
        CreateUser.delete();
    }

    @Test
    @DisplayName("Изменение данных зарегистрированного юзера")
    @Description("Изменение данных зарегистрированного юзера")
    public void changeDataUserWithAuthorization() {
        CreateUser.userRegistration();
        CreateUser.setAccessToken();
        CreateUser.userRegistration();
        CreateUser.userRefreshWithAuthorization(CreateUser.getAccessToken());
        CreateUser.getDataOfUser();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}
