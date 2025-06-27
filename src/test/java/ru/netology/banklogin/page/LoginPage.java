package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.banklogin.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    // Добавляем метод для невалидного входа
    public void invalidLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldBe(visible)
                .shouldHave(exactText(expectedText));
    }

    // Добавляем метод для очистки полей
    public void clearFields() {
        loginField.doubleClick().press(Keys.BACK_SPACE);
        passwordField.doubleClick().press(Keys.BACK_SPACE);

        // Дополнительная проверка
        loginField.shouldBe(empty);
        passwordField.shouldBe(empty);
    }

    public void verifyFieldsAreEmpty() {
        loginField.shouldBe(empty);
        passwordField.shouldBe(empty);
    }
}
