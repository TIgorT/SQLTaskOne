package ru.netology.page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    public final SelenideElement personalAccount = $("[data-test-id='dashboard']");

    public DashboardPage(){
        personalAccount.shouldBe(visible);
    }
}
