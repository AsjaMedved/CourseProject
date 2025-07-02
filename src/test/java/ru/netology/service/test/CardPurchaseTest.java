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
        validField = DataHelper.getValidField();

    }

    @Test
    @DisplayName("Валидные данные")
    void validFieldFilling() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
          purchase.successfulSubmissionOfTheForm("Операция одобрена Банком.");

        String status = DBUtils.getValidVerificationStatus();
        assertEquals("APPROVED", status);

    }

    @Test
    @DisplayName("отправка формы с пустыми полями")
    void emptyFields() {
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
    void emptyMonthField() {
        purchase.buyingAtour(validField.getNumber(), "", validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с пустым полем год")
    void emptyYearField() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), "", validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с пустым полем владелец")
    void emptyOwnerField() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), "", validField.getCvc());
        purchase.errorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("отправка формы с пустым полем CVC/CVV")
    void emptyCvcField() {
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), "");
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с невалидным значением поля номер карты")
    void invalidCardNumber() {
        DataHelper.CardData invalidNumber = DataHelper.getInvalidCardNumber();
        purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.failedSubmissionOfTheForm("Ошибка! Банк отказал в проведении операции.");
    }

     @Test
    @DisplayName("проверка ввода букв в поле номер карты ")
    void lettersInTheCardNumberField() {
         purchase.buttonBuy();
         DataHelper.CardData invalidNumber = DataHelper.getlettersInTheCardNumberField();
         purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.cardNumber("");
    }

    @Test
    @DisplayName("отправка формы с вводом спец символов в поле номер карты ")
    void specialSymbolsInTheCardNumberField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidNumber = DataHelper.getSpecialSymbolsInTheCardNumberField();
        purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.cardNumber("");
    }

    @Test
    @DisplayName("отправка формы с одним символом в поле номер карты")
    void oneCharacterInTheCardNumberField() {
        DataHelper.CardData invalidNumber = DataHelper.getOneCharacterInTheCardNumberField();
        purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("проверка ввода 17 символов в поле номер карты")
    void characters17InTheCardNumberField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidNumber = DataHelper.getCharacters17InTheCardNumberField();
        purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.cardNumber("1111 2222 3333 4444");
    }

    @Test
    @DisplayName("отправка формы с заблокированной картой вполе номер карты")
    void theBlockedCard() {
        DataHelper.CardData invalidNumber = DataHelper.getTheBlockedCard();
        purchase.buyingAtour(invalidNumber.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.failedSubmissionOfTheForm("Ошибка! Банк отказал в проведении операции.");

        String status = DBUtils.getValidVerificationStatus();
        assertEquals("DECLINED", status);
    }

    @Test
    @DisplayName("отправка формы с не валидным значением в поле месяц")
    void invalidMonth() {
        DataHelper.CardData invalidMonth = DataHelper.getInvalidMonth();
        purchase.buyingAtour(validField.getNumber(), invalidMonth.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("ввод букв в поле месяц")
    void lettersInTheMonthField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidMonth = DataHelper.getLettersInTheMonthField();
        purchase.buyingAtour(validField.getNumber(), invalidMonth.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.month("");
    }

    @Test
    @DisplayName("ввод спецсимволов в поле месяц")
    void specialCharactersInTheMonthField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidMonth = DataHelper.getSpecialCharactersInTheMonthField();
        purchase.buyingAtour(validField.getNumber(), invalidMonth.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.month("");
    }

    @Test
    @DisplayName("ввод трех символов в поле месяц")
    void threeCharacterInTheMonthFDield() {
        purchase.buttonBuy();
        DataHelper.CardData invalidMonth = DataHelper.getЕhreeCharacterInTheMonthFDield();
        purchase.buyingAtour(validField.getNumber(), invalidMonth.getMonth(), validField.getYear(), validField.getHolder(), validField.getCvc());
        purchase.month("22");
    }

    @Test
    @DisplayName("истекший срок года карты")
    void expiredYearPeriod() {
        DataHelper.CardData invalidYear = DataHelper.getExpiredYearPeriod();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Истёк срок действия карты");
    }

    @Test
    @DisplayName("ввод спец символов в поле год")
    void specialCharactersInTheYearField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidYear = DataHelper.getSpecialCharactersInTheYearField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.year("");
    }

    @Test
    @DisplayName("отправка формы с одним сиволов в поле год")
    void oneCharacterInTheYearField() {
        DataHelper.CardData invalidYear = DataHelper.getOneCharacterInTheYearField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("ввод трех символов в поле год")
    void threeCharacterInTheYearField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidYear = DataHelper.getThreeCharacterInTheYearField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.year("33");
    }

    @Test
    @DisplayName("ввод букв в поле год")
    void lettersInTheYearField() {
        purchase.buttonBuy();
        DataHelper.CardData invalidYear = DataHelper.getLettersInTheYearField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.year("");
    }

    @Test
    @DisplayName("ввод карты сроком более шести лет")
    void theCardIsValidForMoreThan6Years() {
        DataHelper.CardData invalidYear = DataHelper.getTheCardIsValidForMoreThan6Years();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), invalidYear.getYear(), validField.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("ввод цифр в поле владелец")
    void thenumbersinTheOwnerField() {
        DataHelper.CardData invalidOwner = DataHelper.getThenumbersinTheOwnerField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), invalidOwner.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("отправка формы с одним символом в поле владелец")
    void oneCharacterInTheOwnerField() {
        DataHelper.CardData invalidOwner = DataHelper.getOneCharacterInTheOwnerField();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), invalidOwner.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }
    @Test
    @DisplayName("отправка формы с невалидным значением в поле владелец")
    void invalidOwner() {
        DataHelper.CardData invalidOwner = DataHelper.getInvalidOwner();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), invalidOwner.getHolder(), validField.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("ввод букв в поле cvc")
    void lettersInCvc() {
        purchase.buttonBuy();
        DataHelper.CardData invalidCvc = DataHelper.getLettersInCvc();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), invalidCvc.getCvc());
        purchase.cvc("");
    }

    @Test
    @DisplayName("отправка формы с одним символов в поле cvc")
    void oneCharacterInCvc() {
        DataHelper.CardData invalidCvc = DataHelper.getOneCharacterInCvc();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), invalidCvc.getCvc());
        purchase.errorMessage("Неверный формат");
    }

    @Test
    @DisplayName("ввод четырех символов в поле cvc")
    void fourCharactersInCvc() {
        purchase.buttonBuy();
        DataHelper.CardData invalidCvc = DataHelper.getFourCharactersInCvc();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), invalidCvc.getCvc());
        purchase.cvc("999");
    }

    @Test
    @DisplayName("ввод спецсимволов  в поле cvc")
    void tspecialCharactersInCvc() {
        purchase.buttonBuy();
        DataHelper.CardData invalidCvc = DataHelper.getTspecialCharactersInCvc();
        purchase.buyingAtour(validField.getNumber(), validField.getMonth(), validField.getYear(), validField.getHolder(), invalidCvc.getCvc());
        purchase.cvc("");
    }
}
