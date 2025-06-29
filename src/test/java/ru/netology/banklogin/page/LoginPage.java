package ru.netology.banklogin.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.banklogin.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

/**
 * Страница авторизации пользователя.
 * Предоставляет методы для ввода учетных данных и проверки ошибок.
 */
public class LoginPage {

    // Локаторы элементов
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    /**
     * Выполняет успешный вход с валидными учетными данными.
     *
     * @param info объект с валидным логином и паролем
     * @return новая страница верификации
     */
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        enterCredentials(info);
        return new VerificationPage();
    }

    /**
     * Выполняет попытку входа с неверным паролем, но корректным логином.
     *
     * @param info объект с валидным логином и невалидным паролем
     */
    public void loginWithInvalidPassword(DataHelper.AuthInfo info) {
        enterCredentials(info);
    }

    /**
     * Выполняет попытку входа с неверным логином, но корректным паролем.
     *
     * @param info объект с невалидным логином и валидным паролем
     */
    public void loginWithInvalidLogin(DataHelper.AuthInfo info) {
        enterCredentials(info);
    }

    /**
     * Выполняет попытку входа с невалидным логином и паролем.
     *
     * @param info объект с невалидным логином и паролем
     */
    public void loginWithInvalidBoth(DataHelper.AuthInfo info) {
        enterCredentials(info);
    }

    /**
     * Проверяет отображение сообщения об ошибке.
     *
     * @param expectedText ожидаемый текст ошибки
     */
    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedText));
    }

    /**
     * Очищает поля ввода логина и пароля перед следующей попыткой.
     */
    public void clearFields() {
        loginField.clear();
        passwordField.clear();
    }

    /**
     * Вводит учетные данные в соответствующие поля формы.
     *
     * @param info объект с логином и паролем
     */
    private void enterCredentials(DataHelper.AuthInfo info) {
        clearFields();
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }
}