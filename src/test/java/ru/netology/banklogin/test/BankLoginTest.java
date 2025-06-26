package ru.netology.banklogin.test;

import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.banklogin.data.SQLHelper.cleanAuthCodes;

// docker compose exec mysql mysql -u app -p app
// java -jar ./app-deadline.jar


public class BankLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {

        SQLHelper.cleanAuthCodes(); // очищаем только коды
        loginPage = open("http://localhost:9999", LoginPage.class);
    }


    @AfterEach
    void tearDown() {
        // Удаление auth-кодов из базы данных после каждого теста.
        // Это гарантирует, что коды не будут мешать последующим тестам.
        cleanAuthCodes();
    }

    /**
     * Тест: Успешный вход в систему с валидными учетными данными.
     * Проверяет корректное прохождение авторизации и верификации.
     */

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(String.valueOf(verificationCode));
    }

    /**
     * Тест: Блокировка пользователя после трех неудачных попыток ввода пароля.
     * Проверяет механизм блокировки пользователя при превышении количества неверных попыток.
     */
    @Test
    void shouldBlockUserAfterThreeInvalidPasswordAttempts() {
        var validAuthInfo = DataHelper.getAuthInfoWithTestData();
        var invalidPassword = "wrongness";

        // 3 попытки входа с неправильным паролем
        for (int i = 0; i < 3; i++) {
            loginPage.validLogin(new DataHelper.AuthInfo(validAuthInfo.getLogin(), invalidPassword));
            loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
        }

        // 4-я попытка с правильным паролем — пользователь уже заблокирован
        loginPage.validLogin(validAuthInfo);
        loginPage.verifyErrorNotification("Пользователь заблокирован");

        // Проверяем статус пользователя в БД
        var status = SQLHelper.getUserStatus("vasya");
        assertEquals("blocked", status);
    }
}
