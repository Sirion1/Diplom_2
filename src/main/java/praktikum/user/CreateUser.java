package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class CreateUser {

        static UserGetter.User newUser = new UserGetter.User();
        private static String accessToken;
        private static Response response;

    public static Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public static String getAccessToken() {
            return accessToken;
        }

        public static void setAccessToken() {
            accessToken = response.then().extract().path("accessToken");
        }

        @Step("Регистрация пользователя")
        public static void userRegistration() {
            response = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .log().all()
                    .body(newUser.createNewUser())
                    .when()
                    .post("auth/register");
        }

    @Step("Регистрация пользователя c одинаковыми данными")
    public static void userRegistrationWithDobleData() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", "qwerty@mail.ru");
        dataMap.put("password", "Qwerty123");
        response = given()
                .spec(RestAssureUser.getRestAssureUser())
                .body(dataMap)
                .when()
                .log().all()
                .post("auth/register");
    }


    @Step("Авторизация юзера с емейлом и паролем")
        public static void userLogIn() {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("email", newUser.getEmail());
            dataMap.put("password", newUser.getPassword());
            response = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .body(dataMap)
                    .when()
                    .log().all()
                    .post("auth/login");
        }
    @Step("Регистрация пользователя c одинаковыми данными")
    public static void userRegistrationWithoutEmail() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", null);
        dataMap.put("password", "Qwerty123");
        response = given()
                .spec(RestAssureUser.getRestAssureUser())
                .body(dataMap)
                .when()
                .log().all()
                .post("auth/register");
    }
    @Step("Авторизация юзера с неверным емейлом и паролем")
    public static void userIncorrectDataLOgin() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", "qwerty@mail.ru");
        dataMap.put("password", "Qwerty123");
        response = given()
                .spec(RestAssureUser.getRestAssureUser())
                .body(dataMap)
                .when()
                .log().all()
                .post("auth/login");
    }

        @Step("Получение данных о юзере")
        public static void getDataOfUser() {
            response = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("Authorization", accessToken)
                    .when()
                    .get("auth/user");
        }

        @Step("Смена пароля авторизованного пользователя")
        public void userChangePassword(String accessToken) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("password", "Qwerty123");
            response = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("Authorization", accessToken)
                    .body(dataMap)
                    .when()
                    .log().all()
                    .patch("auth/user");
        }

        @Step("Обновление данных юзера")
        public static void userRefreshWithoutAuthorization() {
            response = given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .body(UserGetter.User.user.createNewUser())
                    .when()
                    .patch("auth/user");
        }

    @Step("Обновление данных юзера")
    public static void userRefreshWithAuthorization(String accessToken) {
        response = given()
                .spec(RestAssureUser.getRestAssureUser())
                .body(UserGetter.User.user.createNewUser())
                .when()
                .patch("auth/user");
    }

        @Step("Удаление юзера")
        public static void delete() {
            if (getAccessToken() == null) return;
            given()
                    .spec(RestAssureUser.getRestAssureUser())
                    .headers("Authorization", accessToken)
                    .when()
                    .delete("auth/user")
                    .then()
                    .statusCode(SC_ACCEPTED);
            System.out.println(getAccessToken());
        }
}

