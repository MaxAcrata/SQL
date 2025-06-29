package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));
    private static final String VALID_LOGIN = "vasya";
    private static final String VALID_PASSWORD = "qwerty123";
    private static final int MIN_PASS_LENGTH = 3;
    private static final int MAX_PASS_LENGTH = 16;

    private DataHelper() {

    }

    /**
     * Возвращает AuthInfo объект с корректным логином и корректным паролем
     */
    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo(VALID_LOGIN, VALID_PASSWORD);
    }

    /**
     * Возвращает AuthInfo объект с корректным логином и некорректным паролем
     */
    public static AuthInfo getInvalidAuthInfo_InvalidPassword() {
        return new AuthInfo(VALID_LOGIN, getInvalidPassword());
    }


    /**
     * Возвращает пароль, отличный от валидного
     */
    public static String getInvalidPassword() {
        String invalidPassword;
        do {
            invalidPassword = generateRandomPassword();
        } while (invalidPassword.equals(VALID_PASSWORD));
        return invalidPassword;
    }


    /**
     * Генерирует случайный пароль.
     */
    public static String generateRandomPassword() {
        return FAKER.internet().password(MIN_PASS_LENGTH, MAX_PASS_LENGTH, true, true);
    }


    @Value
    public static class AuthInfo {
        String login;
        String password;
    }


}