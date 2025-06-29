package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.Getter;
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
     * Возвращает AuthInfo с валидными данными пользователя.
     *
     * @return объект с корректным логином и корректным паролем
     */
    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo(VALID_LOGIN, VALID_PASSWORD);
    }

    /**
     * Возвращает AuthInfo с валидным логином и случайным (некорректным) паролем.
     *
     * @return объект с корректным логином и некорректным паролем
     */
    public static AuthInfo getInvalidAuthInfo_InvalidPassword() {
        return new AuthInfo(VALID_LOGIN, getInvalidPassword());
    }

    /**
     * Возвращает AuthInfo с невалидным логином и корректным паролем.
     *
     * @return объект со случайным логином и корректным паролем
     */
    public static AuthInfo getInvalidAuthInfo_InvalidLogin() {
        return new AuthInfo(getInvalidLogin(), VALID_PASSWORD);
    }

    /**
     * Возвращает валидный логин.
     *
     * @return строка с корректным логином
     */
    public static String getValidLogin() {
        return VALID_LOGIN;
    }

    /**
     * Возвращает валидный пароль.
     *
     * @return строка с паролем "qwerty123"
     */
    public static String getValidPassword() {
        return VALID_PASSWORD;
    }

    /**
     * Возвращает случайный (некорректный) пароль.
     *
     * @return строка с паролем, отличным от "qwerty123"
     */
    public static String getInvalidPassword() {
        String invalidPassword;
        do {
            invalidPassword = generateRandomPassword();
        } while (invalidPassword.equals(VALID_PASSWORD));
        return invalidPassword;
    }

    /**
     * Возвращает случайный (некорректный) логин.
     *
     * @return строка со случайным логином
     */
    public static String getInvalidLogin() {
        return FAKER.name().username();
    }

    /**
     * Генерирует случайный пароль.
     *
     * @return строка с паролем длиной от 8 до 16 символов
     */
    public static String generateRandomPassword() {
        return FAKER.internet().password(MIN_PASS_LENGTH, MAX_PASS_LENGTH, true, true);
    }

    /**
     * Генерирует случайный проверочный код.
     *
     * @return объект VerificationCode с 5-значным числовым кодом
     */
    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("#####"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Getter
    public static class VerificationCode {
        private final String code;

        public VerificationCode(String code) {
            this.code = code;
        }

    }
}