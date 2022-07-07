package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

    public class RestAssureOrderAndRecipe extends RestAssureUser {
        private List<String> listOfIngridients;
        private Response responseOrder;

        @Step("Получение ответа")
        public Response getResponseOrder() {
            return responseOrder;
        }

        @Step("Список ингридиентов")
        public void setlistOfIngridients() {
            listOfIngridients = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .get("ingredients")
                    .then()
                    .extract()
                    .path("data._id");
        }

        @Step("Заказ с авторизацией через токен")
        public void makeOrderWithToken(String accessToken) {
            Random random = new Random();
            String randomIngredients = listOfIngridients.get(random.nextInt(listOfIngridients.size()));
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("ingredients", randomIngredients);
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("authorization", accessToken)
                    .body(dataMap)
                    .when()
                    .post("orders");
        }

        @Step("Заказ без авторизации")
        public void makeOrderWithoutToken() {
            Random random = new Random();
            String randomIngredients =  null;
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("ingredients", randomIngredients);
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .body(dataMap)
                    .when()
                    .get("orders");
        }

        @Step("Создание пустого заказа")
        public void makeOrderWithoutIngridients(String accessToken) {
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("authorization", accessToken)
                    .when()
                    .post("orders");
        }

        @Step("Создание заказа с неверным хэшэм ингредиентов")
        public void makeOrderWithoutCorrectHash(String accessToken) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("ingredients", "invalidHash");
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("authorization", accessToken)
                    .body(dataMap)
                    .when()
                    .post("orders");
        }

        @Step("Список заказов авторизированного пользователя")
        public void getOrderRecipeWithAuthorization(String accessToken) {
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("authorization", accessToken)
                    .when()
                    .get("orders");
        }

        @Step("Список заказов неавторизованного пользователя")
        public void getOrderRecipeWithoutAuthorization() {
            responseOrder = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .when()
                    .get("orders");
        }
    }