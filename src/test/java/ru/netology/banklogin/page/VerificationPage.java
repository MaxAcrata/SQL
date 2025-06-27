package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    //private final SelenideElement errorNotification = $("[data-test-id='error-notification') .notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);

    }


    public void validVerify(String verificationCode) {
        verify(verificationCode);
        new DashboardPage();

    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();

    }
}
