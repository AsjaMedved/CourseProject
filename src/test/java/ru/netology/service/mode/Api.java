package ru.netology.service.mode;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.netology.service.data.DataHelper;

import static io.restassured.RestAssured.given;

public class Api {
    private static final String baseUri = "http://localhost:9999";

    private Api() {
    }

    public static Response buyTour(DataHelper.CardData cardData) {
            return given()
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .body(cardData)
                    .when()
                    .post("/api/v1/pay");
        }
    }

