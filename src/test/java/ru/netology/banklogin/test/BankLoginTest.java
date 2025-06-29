package ru.netology.banklogin.test;

import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;
import ru.netology.banklogin.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        loginPage.login(authInfo);

        var verificationCode = SQLHelper.getVerificationCode();
        new VerificationPage().validVerify(verificationCode);
    }

    @Disabled
    @Test
    void shouldBlockUserAfterThreeInvalidPasswordAttempts() {
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo_InvalidPassword();

        for (int i = 1; i <= 3; i++) {
            loginPage.clearFields();
            loginPage.login(invalidAuthInfo);
            loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

            if (i < 3) {
                assertEquals("active", SQLHelper.getUserStatus("vasya"));
            } else {
                assertEquals("blocked", SQLHelper.getUserStatus("vasya"));
            }
        }
    }
}
