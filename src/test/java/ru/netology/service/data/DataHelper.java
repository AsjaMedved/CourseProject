package ru.netology.service.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getApprovedCardNumber () {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCardNumber () {
        return "5555 6666 7777 8888";
    }

    public static String generateMonth(int shift) {
        return LocalDate.now().plusMonths(shift)
                .format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int shift) {
        return LocalDate.now().plusYears(shift)
                .format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidHolder() {
        return faker.name().fullName();
    }

    public static String generateValidCVC() {
        return faker.number().digits(3);
    }

    public static CardData getValidField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getInvalidCardNumber() {
        return new CardData("0000 0000 0000 0000", generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getlettersInTheCardNumberField() {
        return new CardData("кккк кккк кккк кккк", generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getSpecialSymbolsInTheCardNumberField() {
        return new CardData("**** !!!! .... ????", generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getOneCharacterInTheCardNumberField() {
        return new CardData("2", generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getCharacters17InTheCardNumberField() {
        return new CardData("1111 2222 3333 4444 5", generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getTheBlockedCard() {
        return new CardData(getDeclinedCardNumber(), generateMonth(0), generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getInvalidMonth() {
        return new CardData(getApprovedCardNumber(), "00", generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getLettersInTheMonthField() {
        return new CardData(getApprovedCardNumber(), "ЛЛ", generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getSpecialCharactersInTheMonthField() {
        return new CardData(getApprovedCardNumber(), "!!", generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getOneCharacterInTheMonthFDield() {
        return new CardData(getApprovedCardNumber(), "2", generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getЕhreeCharacterInTheMonthFDield() {
        return new CardData(getApprovedCardNumber(), "222", generateYear(0), generateValidHolder(), generateValidCVC());
    }

    public static CardData getExpiredYearPeriod() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), generateYear(-2), generateValidHolder(), generateValidCVC());
    }

    public static CardData getSpecialCharactersInTheYearField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), "!!", generateValidHolder(), generateValidCVC());
    }

    public static CardData getOneCharacterInTheYearField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), "!!", generateValidHolder(), generateValidCVC());
    }

    public static CardData getThreeCharacterInTheYearField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), "333", generateValidHolder(), generateValidCVC());
    }

    public static CardData getLettersInTheYearField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0), "ЛЛ", generateValidHolder(), generateValidCVC());
    }

    public static CardData getTheCardIsValidForMoreThan6Years() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), generateValidHolder(), generateValidCVC());
    }

    public static CardData getThenumbersinTheOwnerField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), "12345", generateValidCVC());
    }

    public static CardData getOneCharacterInTheOwnerField() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), "П", generateValidCVC());
    }

    public static CardData getInvalidOwner() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), "аааааааааааааа", generateValidCVC());
    }

    public static CardData getLettersInCvc() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), generateValidHolder(), "ккк");
    }

    public static CardData getOneCharacterInCvc() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), generateValidHolder(), "9");
    }

    public static CardData getFourCharactersInCvc() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), generateValidHolder(), "9999");
    }

    public static CardData getTspecialCharactersInCvc() {
        return new CardData(getApprovedCardNumber(), generateMonth(0),  generateYear(6), generateValidHolder(), "!!");
    }



    @Value
    public static class CardData {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }
}

