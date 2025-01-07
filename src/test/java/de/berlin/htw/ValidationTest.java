package de.berlin.htw;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ValidationTest {

    @Test
    void testInvalidProductName() {
        given()
                .header("X-User-Id", "1")
                .contentType(ContentType.JSON)
                .body("{ \"productName\": \"" + "a".repeat(256) + "\", \"productId\": \"1-2-3-4-5-6\", \"price\": 50, \"count\": 1 }")
                .when()
                .post("/basket/123")
                .then()
                .statusCode(400);
    }

    @Test
    void testInvalidProductId() {
        given()
                .header("X-User-Id", "1")
                .contentType(ContentType.JSON)
                .body("{ \"productName\": \"ValidName\", \"productId\": \"12345\", \"price\": 50, \"count\": 1 }")
                .when()
                .post("/basket/123")
                .then()
                .statusCode(400);
    }

    @Test
    void testInvalidPrice() {
        given()
                .header("X-User-Id", "1")
                .contentType(ContentType.JSON)
                .body("{ \"productName\": \"ValidName\", \"productId\": \"1-2-3-4-5-6\", \"price\": 5, \"count\": 1 }")
                .when()
                .post("/basket/123")
                .then()
                .statusCode(400);
    }
/*
    @Test
    void testExceedingBasketSize() {
        String userId = "123";
        String itemJsonTemplate = "{ \"productName\": \"Item\", \"productId\": \"1-2-3-4-5-6-%d\", \"price\": 50.0, \"count\": 1 }";

        for (int i = 1; i <= 11; i++) {
            String itemJson = String.format(itemJsonTemplate, i);
            given()
                    .header("X-User-Id", userId)
                    .contentType(ContentType.JSON)
                    .body(itemJson)
                    .when()
                    .post("/basket/" + i)
                    .then()
                    .statusCode(i <= 10 ? 201 : 400);
        }
    }

    @Test
    void testValidInput() {
        given()
                .header("X-User-Id", "1")
                .contentType(ContentType.JSON)
                .body("{ \"productName\": \"ValidName\", \"productId\": \"1-2-3-4-5-6\", \"price\": 50, \"count\": 1 }")
                .when()
                .post("/basket/123")
                .then()
                .statusCode(201);
    }
*/
}
