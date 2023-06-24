package ru.netology.test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class BankLoginTest {
    @AfterAll
    static void teardown() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Успешный вход в личный кабинет системы")
    public void successfulLoginToThePersonalAccountOfTheSystem() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTheInformationOfARegisteredUser();
        var verificationPage = loginPage.validLoginData(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getTheVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Вход используя зарегистрированный логин и рандомный пароль")
    public void loginUsingARegisteredUsernameAndARandomPassword() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredUsernameAndARandomPassword(DataHelper.getTheInformationOfARegisteredUser().getLogin()
                , DataHelper.getARandomPassword(), "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Вход в систему с пустым полем логина")
    public void LogInWithAnEmptyLoginField() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        loginPage.errorInTheSystemEmptyLoginField(DataHelper.getTheInformationOfARegisteredUser().getPassword(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Вход используя зарегистрированный  пароль  и рандомный логин")
    public void loginUsingARegisteredPasswordAndARandomUsername() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredPasswordAndARandomUsername(DataHelper.getARandomLogin()
                , DataHelper.getTheInformationOfARegisteredUser().getPassword(), "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Вход в систему с пустым полем пароля")
    public void LogInWithAnEmptyPasswordField() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        loginPage.errorInTheSystemEmptyPasswordField(DataHelper.getTheInformationOfARegisteredUser().getLogin(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Вход в систему  незарегистрированным пользователем")
    public void loggingInUnregisteredUser() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTheInformationOfARandomUser();
        loginPage.validLoginData(authInfo);
        loginPage.errorInTheSystemInvalidPasswordOrLogin("Ошибка! Неверно указан логин или пароль");
    }


    @Test
    @DisplayName("Вход в систему используя рандомный код подтверждения")
    public void logInUsingARandomConfirmationCode() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTheInformationOfARegisteredUser();
        var verificationPage = loginPage.validLoginData(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.getARandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.errorRandomConfirmationCode("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }


    @Test
    @DisplayName("Вход в систему с пустым полем  код смс")
    public void LoggingInWithAnEmptySmsCodeField() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTheInformationOfARegisteredUser();
        var verificationPage = loginPage.validLoginData(authInfo);
        verificationPage.verifyErrorNotificationPageVisiblity("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Вход в систему используя устаревший  код подтверждения")
    public void logInUsingAnOutdatedConfirmationCode() {
        SQLHelper.cleaningTheTableWithVerificationCodes();
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTheInformationOfARegisteredUser();
        var verificationPage = loginPage.validLoginData(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getTheVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

        open("http://localhost:9999/", LoginPage.class);
        loginPage.validLoginData(authInfo);
        verificationPage.verifyVerificationPageVisiblity();

        verificationCode = SQLHelper.getOutdatedTheVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.errorOutdatedVerificationCode("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }


    @Test
    @DisplayName("Вход в систему с использованием  неправильного пароля три раза")
    public void LoggingInUsingTheWrongPasswordThreeTimesTextFirst() {

        var loginPage = open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredUsernameAndARandomPassword(DataHelper.getTheInformationOfARegisteredUser().getLogin()
                , DataHelper.getARandomPassword(), "Ошибка! Неверно указан логин или пароль");

        open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredUsernameAndARandomPassword(DataHelper.getTheInformationOfARegisteredUser().getLogin()
                , DataHelper.getARandomPassword(), "Ошибка! Неверно указан логин или пароль");

        open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredUsernameAndARandomPassword(DataHelper.getTheInformationOfARegisteredUser().getLogin()
                , DataHelper.getARandomPassword(), "Ошибка! Неверно указан логин или пароль");


        var authInfo = DataHelper.getTheInformationOfARegisteredUser();
        Assertions.assertEquals("blocked", SQLHelper.getUserStatus(authInfo.getLogin()));


        open("http://localhost:9999/", LoginPage.class);
        loginPage.logInUsingARegisteredUsernameAndARandomPassword(DataHelper.getTheInformationOfARegisteredUser().getLogin()
                , DataHelper.getTheInformationOfARegisteredUser().getPassword(), "Ошибка! Данный пользователь заблокирован");
    }
}
