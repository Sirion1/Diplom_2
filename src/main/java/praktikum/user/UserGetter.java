package praktikum.user;

import lombok.Getter;

import java.util.Random;

public class UserGetter {
    @Getter
    public static class User {
        static User user = new User();
        private String name;
        private String email;
        private String password;

        public User createNewUser() {
            Random random = new Random();
            email = (random.nextInt(10000)) + "test@gmail.com";
            password = "Qwerty123";
            name = "Vasya_1";
            return this;
        }
    }
}

