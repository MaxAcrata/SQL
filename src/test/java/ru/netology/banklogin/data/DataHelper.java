package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {

    }

    /**
     * Возвращает объект AuthInfo с предопределенными тестовыми данными.
     * Эти данные должны быть известны и присутствовать в тестовой базе данных.
     *
     * @return AuthInfo объект с логином "vasya" и паролем "qwerty123".
     */
    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String generateRandomLogin() {
        return FAKER.name().username();

    }

    private static String generateRandomPassword() {
        return FAKER.internet().password();

    }


    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("#####"));


    }

    @Value
    public static class AuthInfo {
        String login;
        String password;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }

}
