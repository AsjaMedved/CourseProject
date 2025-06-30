package ru.netology.service.mode;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.netology.service.data.DataHelper;

import static io.restassured.RestAssured.given;

public class Api {

        private final String baseUri;

        public Api (String baseUri) {
            this.baseUri = baseUri;
        }

        public Response buyTour(DataHelper.CardData cardData) {
            return given()
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .body(cardData)
                    .when()
                    .post("/api/v1/pay");
        }
    }

