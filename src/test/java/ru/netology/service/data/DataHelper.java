package ru.netology.service.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    public static CardData getValidCard() {
        return new CardData("1111 2222 3333 4444", "12", "25", "Иванов Иван", "999");
    }

    public static CardData getInvalidCardNumber() {
        return new CardData("0000 0000 0000 0000", "12", "25", "Иванов Иван", "999");
    }

    public static CardData getEnteringASingleCharacter() {
        return new CardData("9", "12", "25", "Иванов Иван", "999");
    }

    public static CardData getBlockedCard() {
        return new CardData("5555 6666 7777 8888", "12", "25", "Иванов Иван", "999");
    }

    public static CardData getInvalidMonth() {
        return new CardData("1111 2222 3333 4444", "00", "25", "Иванов Иван", "999");
    }

    public static CardData getEnteringOneSimInTheMonthField() {
        return new CardData("1111 2222 3333 4444", "3", "25", "Иванов Иван", "999");
    }

    public static CardData getExpiredCardPeriod() {
        return new CardData("1111 2222 3333 4444", "12", "11", "Иванов Иван", "999");
    }

    public static CardData getOneCharacterInTheYearField() {
        return new CardData("1111 2222 3333 4444", "12", "9", "Иванов Иван", "999");
    }

    public static CardData getTheCardIsValidSorSixYears() {
        return new CardData("1111 2222 3333 4444", "12", "31", "Иванов Иван", "999");
    }

    public static CardData getNumbersInTheOwnerField() {
        return new CardData("1111 2222 3333 4444", "12", "25", "12345", "999");
    }

    public static CardData getOneCharacterInTheOwnerField() {
        return new CardData("1111 2222 3333 4444", "12", "25", "П", "999");
    }

    public static CardData getInvalidOwnerFieldValue() {
        return new CardData("1111 2222 3333 4444", "12", "25", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "999");
    }

    public static CardData getOneCharacterInTheCvcField() {
        return new CardData("1111 2222 3333 4444", "12", "31", "Иванов Иван", "5");
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

