package ru.netology.service.test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import ru.netology.service.data.DataHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @Test
    void shouldApprovePaymentWithValidCard() {
        var validCard = DataHelper.getValidCard();

        given()
                .baseUri("http://localhost:9999")
                .contentType(ContentType.JSON)
                .body(validCard)

                .when()
                .post("api/v1/pay")

                .then()
                .log().body()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }
}
