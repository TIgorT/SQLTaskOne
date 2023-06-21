package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {

    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static AuthInfo getTheInformationOfARegisteredUser() {
        return new AuthInfo("vasya", "qwerty123");

    }

    public static String getARandomLogin() {
        return faker.name().username();
    }

    public static String getARandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getTheInformationOfARandomUser() {
        return new AuthInfo(getARandomLogin(), getARandomPassword());
    }

    public static VerificationCode getARandomVerificationCode() {
        return new VerificationCode(faker.numerify("#######"));
    }

}
