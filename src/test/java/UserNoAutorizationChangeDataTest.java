import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.UserGetter;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserNoAutorizationChangeDataTest {
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
    @DisplayName("Изменение данных неавторизованного юзера")
    @Description("Изменение данных неавторизованного юзера")
    public void changeDataUserWithoutAuthorization() {
        CreateUser.userRefreshWithoutAuthorization();
        CreateUser.getResponse()
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false));
    }

}


