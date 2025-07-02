package ru.netology.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Purchase {

    private SelenideElement fieldButtonBuy = $$("button").findBy(text("Купить"));
    private SelenideElement fieldCardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("[placeholder='08']");
    private SelenideElement fieldYear = $("[placeholder='22']");
    private SelenideElement fieldOwner = $$("input.input__control").get(3);
    private SelenideElement fieldCvc = $("[placeholder='999']");
    private SelenideElement fieldButtonResume = $$("button").findBy(text("Продолжить"));
    private SelenideElement registrationMessage = $(".notification__content");
    private ElementsCollection errorField = $$(".input__sub");
    private SelenideElement errorWindow = $(".notification_status_error .notification__content");

    public void buyingAtour(String cardNumber, String month, String year, String owner, String cvc) {
        fieldButtonBuy.click();
        fieldCardNumber.setValue(cardNumber);
        fieldMonth.setValue(month);
        fieldYear.setValue(year);
        fieldOwner.setValue(owner);
        fieldCvc.setValue(cvc);
        fieldButtonResume.click();
    }

    public void buttonBuy() {
        fieldButtonBuy.click();
    }

    public void cardNumber (String value){
        fieldCardNumber.shouldHave(Condition.value(value));
    }

    public void month(String value){
        fieldMonth.shouldHave(Condition.value(value));
    }

    public void year(String value){
        fieldYear.shouldHave(Condition.value(value));
    }

    public void cvc(String value){
        fieldCvc.shouldHave(Condition.value(value));
    }

    public void successfulSubmissionOfTheForm(String expectedText) {
        registrationMessage.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.text(expectedText));
    }

    public void errorMessage(String expectedText) {
        errorField.findBy(Condition.text(expectedText)).shouldBe(Condition.visible);
    }

    public void failedSubmissionOfTheForm(String expectedText) {
        errorWindow.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text(expectedText));
    }

}
