package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));
    private static final String VALID_PASSWORD = "qwerty123";

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", VALID_PASSWORD);
    }

    public static AuthInfo getInvalidAuthInfo() {
        String invalidPassword;
        // Генерируем пароль до тех пор, пока не получим отличный от валидного
        do {
            invalidPassword = generateRandomPassword();
        } while (invalidPassword.equals(VALID_PASSWORD));

        return new AuthInfo("vasya", invalidPassword);
    }


    public static String generateRandomPassword() {
        return FAKER.internet().password(8, 16, true, true);
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