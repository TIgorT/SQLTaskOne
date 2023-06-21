package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginToLogInToTheSystem = $("[data-test-id='login'] .input__control");
    private final SelenideElement passwordToLogInToTheSystem = $("[data-test-id='password'] .input__control");
    private final SelenideElement continueButton = $("[data-test-id='action-login']");
    private final SelenideElement errorYouNeedToFillInTheLoginField = $("[data-test-id='login'] .input__sub");
    private final SelenideElement errorYouNeedToFillInThePasswordField = $("[data-test-id='password'] .input__sub");
    private final SelenideElement errorInvalidPasswordOrLogin = $("[data-test-id='error-notification'] .notification__content");


    public VerificationPage validLoginData(DataHelper.AuthInfo info) {
        loginToLogInToTheSystem.setValue(info.getLogin());
        passwordToLogInToTheSystem.setValue(info.getPassword());
        continueButton.click();
        return new VerificationPage();
    }


    public void errorInTheSystemInvalidPasswordOrLogin(String expectedText) {
        errorInvalidPasswordOrLogin.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorInTheSystemEmptyLoginField(String expectedText) {
        passwordToLogInToTheSystem.setValue(DataHelper.getARandomPassword());
        continueButton.click();
        errorYouNeedToFillInTheLoginField.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorInTheSystemEmptyPasswordField(String expectedText) {
        loginToLogInToTheSystem.setValue(DataHelper.getARandomLogin());
        continueButton.click();
        errorYouNeedToFillInThePasswordField.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void logInUsingARegisteredUsernameAndARandomPassword(String expectedText) {
        loginToLogInToTheSystem.setValue("vasya");
        passwordToLogInToTheSystem.setValue(DataHelper.getARandomPassword());
        continueButton.click();
        errorInvalidPasswordOrLogin.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void logInUsingARegisteredPasswordAndARandomUsername(String expectedText) {
        loginToLogInToTheSystem.setValue(DataHelper.getARandomLogin());
        passwordToLogInToTheSystem.setValue("12345");
        continueButton.click();
        errorInvalidPasswordOrLogin.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }
}

