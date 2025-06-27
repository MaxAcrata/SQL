package ru.netology.banklogin.test;

import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        SQLHelper.resetUserStatus("vasya"); // Сброс статуса
        SQLHelper.createTestUser(); // Создание пользователя
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldBlockUserAfterThreeInvalidPasswordAttempts() {
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo();

        for (int i = 1; i <= 3; i++) {
            loginPage.invalidLogin(invalidAuthInfo);
            loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

            // Проверка статуса после каждой попытки
            if (i < 3) {
                assertEquals("active", SQLHelper.getUserStatus("vasya"),
                        "Ошибка! Неверно указан логин или пароль");
            } else {
                assertEquals("blocked", SQLHelper.getUserStatus("vasya"),
                        "Пользователь заблокирован после 3 попыток неверного ввода ");
            }

            loginPage.clearFields();

            // Явная проверка очистки полей
            loginPage.verifyFieldsAreEmpty();
        }
    }
}
