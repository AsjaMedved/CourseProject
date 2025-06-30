package ru.netology.service.test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import ru.netology.service.data.DataHelper;
import ru.netology.service.mode.Api;
import ru.netology.service.mode.DBUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    private final Api apiClient = new Api("http://localhost:9999");

    @Test
    void purchaseStatusWithaValidCard() {
        var validCard = DataHelper.getValidField();

        var response = apiClient.buyTour(validCard);
        response.then()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));

        String statusInDb = DBUtils.getValidVerificationStatus();
        assertEquals("APPROVED", statusInDb);
    }

    @Test
    void statusWhenBuyingWithAnInvalidCard() {
        var invalidCard = DataHelper.getTheBlockedCard();

        var response = apiClient.buyTour(invalidCard);
        response.then()
                .statusCode(200)
                .body("status", equalTo("DECLINED"));

        String statusInDb = DBUtils.getValidVerificationStatus();
        assertEquals("DECLINED", statusInDb);
    }
}
