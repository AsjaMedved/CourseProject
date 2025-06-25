package ru.netology.service.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.service.data.DataHelper;
import ru.netology.service.mode.DBUtils;
import ru.netology.service.page.Purchase;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardPurchaseTest {

    private Purchase purchase;
    private DataHelper.CardData validField;


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        purchase = new Purchase();
        validField = DataHelper.getValidCard();

    }

    @Test
    @DisplayName("Валидные данные")
    void validFieldFilling() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.successfulSubmissionOfTheForm("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("отправка формы с пустыми полями")
    void invalidFieldFilling() {
        purchase.buyingAtour("", "", "", "", "");
        purchase.errorMessage("Неверный формат");
        purchase.errorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("отправка формы с пустым полем номер карты")
    void emptyCardNumberField() {
        purchase.buyingAtour("", validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с пустым полем месяц")
    void emptMontFhield() {
        purchase.buyingAtour(validField.getNumber(), "", validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с пустым полем год")
    void emptYearFhield() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), "", validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с пустым полем владелец")
    void emptOwnerFhield() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), "", validField.getCvc());
        purchase.errorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("отправка формы с пустым полем cvc")
    void emptCvcFhield() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), "");
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с не валидным полем номер карты ")
    void invalidCardNumber() {
        DataHelper.CardData invalidCard = DataHelper.getInvalidCardNumber();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.failedSubmissionOfTheForm("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("отправка формы с вводом букв в поле номер карты ")
    void enteringLetters() {
        purchase.fieldButtonBuy.click();
        var cardNumber = "РР";
        purchase.fieldCardNumber.setValue(cardNumber);
        purchase.fieldCardNumber.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с вводом спецсимволов в поле номер карты ")
    void enteringSpecialCharacters() {
        purchase.fieldButtonBuy.click();
        var cardNumber = "!!";
        purchase.fieldCardNumber.setValue(cardNumber);
        purchase.fieldCardNumber.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с вводом одного символа в поле номер карты ")
    void enteringASingleCharacter() {
        DataHelper.CardData invalidCard = DataHelper.getEnteringASingleCharacter();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("проверка колличество введеных символов  в поле номер карты - не больше 16 ")
    void entering17Characters() {
        purchase.fieldButtonBuy.click();
        var cardNumber = "1111 2222 3333 4444 5";
        purchase.fieldCardNumber.setValue(cardNumber);
        purchase.fieldCardNumber.shouldHave(Condition.value("1111 2222 3333 4444"));
    }

    @Test
    @DisplayName("отправка формы с заблокированной картой ")
    void blockedCard() {
        DataHelper.CardData invalidCard = DataHelper.getBlockedCard();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.failedSubmissionOfTheForm("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("отправка формы с не валидным полем месяц ")
    void invalidMonth() {
        DataHelper.CardData invalidCard = DataHelper.getInvalidMonth();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("отправка формы с вводом букв в поле месяц ")
    void enteringLettersInTheMonthField() {
        purchase.fieldButtonBuy.click();
        var month = "РР";
        purchase.fieldMonth.setValue(month);
        purchase.fieldMonth.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с вводом спецсимволов в поле месяц ")
    void enteringSpecialCharactersInTheMonthField() {
        purchase.fieldButtonBuy.click();
        var month = "!%";
        purchase.fieldMonth.setValue(month);
        purchase.fieldMonth.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с вводом одного символа в поле месяц ")
    void enteringOneSimInTheMonthField() {
        DataHelper.CardData invalidCard = DataHelper.getEnteringOneSimInTheMonthField();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("проверка колличество введеных символов  в поле месяц - не больше 2 ")
    void enteringThreeCharactersInTheMonthField() {
        purchase.fieldButtonBuy.click();
        var month = "122";
        purchase.fieldMonth.setValue(month);
        purchase.fieldMonth.shouldHave(Condition.value("12"));
    }

    @Test
    @DisplayName("отправка формы с истекшим сроком карты в поле год ")
    void expiredCardPeriod() {
        DataHelper.CardData invalidCard = DataHelper.getExpiredCardPeriod();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Истёк срок действия карты");
    }

    @Test
    @DisplayName("отправка формы с спецсимволами в поле год ")
    void specialCharactersInTheYearField() {
        purchase.fieldButtonBuy.click();
        var year = "!!";
        purchase.fieldYear.setValue(year);
        purchase.fieldYear.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с одним символом в поле год ")
    void oneCharacterInTheYearField() {
        DataHelper.CardData invalidCard = DataHelper.getOneCharacterInTheYearField();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("проверка колличество введеных символов  в поле год - не больше 2 ")
    void threeCharactersInTheYearField() {
        purchase.fieldButtonBuy.click();
        var year = "255";
        purchase.fieldYear.setValue(year);
        purchase.fieldYear.shouldHave(Condition.value("25"));
    }

    @Test
    @DisplayName("отправка формы с вводом букв в поле год")
    void lettersInTheYearField() {
        purchase.fieldButtonBuy.click();
        var year = "РР";
        purchase.fieldYear.setValue(year);
        purchase.fieldYear.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с картой, сроком больше 6 лет в поле год ")
    void theCardIsValidSorSixYears() {
        DataHelper.CardData invalidCard = DataHelper.getTheCardIsValidSorSixYears();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("отправка формы с вводом цифр в поле владелец ")
    void numbersInTheOwnerField() {
        DataHelper.CardData invalidCard = DataHelper.getNumbersInTheOwnerField();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с вводом одного символа в поле владелец ")
    void oneCharacterInTheOwnerField() {
        DataHelper.CardData invalidCard = DataHelper.getOneCharacterInTheOwnerField();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с не валидным значением в поле владелец ")
    void invalidOwnerFieldValue() {
        DataHelper.CardData invalidCard = DataHelper.getInvalidOwnerFieldValue();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с вводом букв в поле cvc")
    void lettersInTheCvcField() {
        purchase.fieldButtonBuy.click();
        var cvc = "РР";
        purchase.fieldCvc.setValue(cvc);
        purchase.fieldCvc.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("отправка формы с вводом одного символа в поле cvc ")
    void oneCharacterInTheCvcField() {
        DataHelper.CardData invalidCard = DataHelper.getOneCharacterInTheCvcField();
        purchase.buyingAtour(invalidCard.getNumber(), invalidCard.getMonth(), invalidCard.getYear(), invalidCard.getHolder(), invalidCard.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("проверка колличество введеных символов  в поле cvc - не больше 3")
    void fourCharactersInTheCvcField() {
        purchase.fieldButtonBuy.click();
        var cvc = "9999";
        purchase.fieldCvc.setValue(cvc);
        purchase.fieldCvc.shouldHave(Condition.value("999"));
    }

    @Test
    @DisplayName("отправка формы с вводом спецсимволов в поле cvc")
    void specialCharactersInTheCvcField() {
        purchase.fieldButtonBuy.click();
        var cvc = "!!";
        purchase.fieldCvc.setValue(cvc);
        purchase.fieldCvc.shouldHave(Condition.value(""));
    }

    @Test
    @DisplayName("Проверка статуса в Postgres")
    void shouldBuyWithValidCardAndCheckStatusInPostgres() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.successfulSubmissionOfTheForm("Операция одобрена Банком.");
        String status = DBUtils.getLastPaymentStatusPostgres();
        assertEquals("APPROVED", status);
    }

    @Test
    @DisplayName("Проверка статуса в Mysql")
    void shouldBuyWithValidCardAndCheckStatusInMysql() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.successfulSubmissionOfTheForm("Операция одобрена Банком.");
        String status = DBUtils.getLastPaymentStatusMysql();
        assertEquals("APPROVED", status);
    }
}
