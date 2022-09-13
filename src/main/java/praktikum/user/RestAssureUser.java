package praktikum.user;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.http.ContentType.JSON;

public class RestAssureUser {

        public static final String DEFAULT_URL = "https://stellarburgers.nomoreparties.site/api/";

        public static RequestSpecification getRestAssureUser() {
            return new RequestSpecBuilder()
                    .setContentType(JSON)
                    .setBaseUri(DEFAULT_URL)
                    .build();
        }
}
