package ru.netology.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Purchase {

    public SelenideElement fieldButtonBuy = $$("button").findBy(text("Купить"));
    public SelenideElement fieldCardNumber = $("[placeholder='0000 0000 0000 0000']");
    public SelenideElement fieldMonth = $("[placeholder='08']");
    public SelenideElement fieldYear = $("[placeholder='22']");
    public SelenideElement fieldOwner = $$("input.input__control").get(3);
    public SelenideElement fieldCvc = $("[placeholder='999']");
    public SelenideElement fieldButtonResume = $$("button").findBy(text("Продолжить"));
    public SelenideElement registrationMessage = $(".notification__content");
    public ElementsCollection errorField = $$(".input__sub");
    public SelenideElement errorWindow = $(".notification_status_error .notification__content");

    public void buyingAtour(String cardNumber, String month, String year, String owner, String cvc) {
        fieldButtonBuy.click();
        fieldCardNumber.setValue(cardNumber);
        fieldMonth.setValue(month);
        fieldYear.setValue(year);
        fieldOwner.setValue(owner);
        fieldCvc.setValue(cvc);
        fieldButtonResume.click();
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
