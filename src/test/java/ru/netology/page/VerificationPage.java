package ru.netology.page;

import com.codeborne.selenide.SelenideElement;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement confirmationCode = $("[data-test-id='code'] .input__control");
    private final SelenideElement verificationButton = $("[data-test-id='action-verify']");
    private final SelenideElement verificationCodeError = $("[data-test-id='error-notification'] .notification__content ");
    private final SelenideElement errorYouNeedToFillInTheField = $("[data-test-id='code'] .input__sub");

    public void verifyVerificationPageVisiblity() {
        confirmationCode.shouldBe(visible);
    }

    public void verifyErrorNotificationPageVisiblity(String expectedText) {
        verificationButton.click();
        errorYouNeedToFillInTheField.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void verify(String verificationCod) {
        confirmationCode.setValue(verificationCod);
        verificationButton.click();
    }

    public DashboardPage validVerify(String verificationCod) {
        verify(verificationCod);
        return new DashboardPage();

    }

    public void errorRandomConfirmationCode(String expectedText) {
        verificationCodeError.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorOutdatedVerificationCode(String expectedText) {
        verificationCodeError.shouldHave(text(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

}
