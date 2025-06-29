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
    private final SelenideElement loginField = $("[data-test-id=login] input").shouldBe(Condition.editable);
    private final SelenideElement passwordField = $("[data-test-id=password] input").shouldBe(Condition.editable);
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    /**
     * Выполняет ввод логина, пароля и отправку формы.
     *
     * @param info объект с логином и паролем
     */
    public void login(DataHelper.AuthInfo info) {
        clearFields();
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
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
     * Очищает поля ввода логина и пароля.
     */
    public void clearFields() {
        loginField.clear();
        passwordField.clear();
    }
}