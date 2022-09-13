import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.CreateUser;
import praktikum.user.RestAssureOrderAndRecipe;
import static org.hamcrest.CoreMatchers.equalTo;


public class OrderTest {

    CreateUser CreateUserTest = new CreateUser();
    RestAssureOrderAndRecipe RestAssureOrderAndRecipeTest = new RestAssureOrderAndRecipe();

    @Before
    public void setUp() {
        CreateUser.userRegistration();
        RestAssureOrderAndRecipeTest.setlistOfIngridients();
    }

    @After
    public void UserDelete() {
        CreateUser.delete();
    }

    @Test
    @DisplayName("Новый заказ с токеном")
    @Description("Заказ авторизованного пользователя")
    public void makeOrderWithAuthorization() {
        CreateUser.userRegistration();
        CreateUserTest.setAccessToken();
        RestAssureOrderAndRecipeTest.makeOrderWithToken(CreateUser.getAccessToken());
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true));

    }

    @Test
    @DisplayName("Создание пустого")
    @Description("Создание пустого заказа")
    public void orderCreationWithoutIngredient() {
        CreateUser.userRegistration();
        CreateUserTest.setAccessToken();
        RestAssureOrderAndRecipeTest.makeOrderWithoutIngridients(CreateUser.getAccessToken());
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Заказ с неверным хешем ингридиентов")
    @Description("Заказ с неверным хешем ингридиентов")
    public void makeOrderWithIncorrectHash() {
        CreateUser.userRegistration();
        CreateUserTest.setAccessToken();
        RestAssureOrderAndRecipeTest.makeOrderWithoutCorrectHash(CreateUser.getAccessToken());
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Получение списка заказов зарегестрированного пользователя")
    @Description("Получение списка заказов зарегестрированного пользователя")
    public void getOrdersWithAuthorization() {
        CreateUser.userRegistration();
        CreateUserTest.setAccessToken();
        RestAssureOrderAndRecipeTest.makeOrderWithToken(CreateUser.getAccessToken());
        RestAssureOrderAndRecipeTest.getOrderRecipeWithAuthorization(CreateUser.getAccessToken());
        RestAssureOrderAndRecipeTest.getResponseOrder()
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true));
    }
}

