package webshop.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BookStoreTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    @Tag("API")
    void withAllLogsTest() {
        step("Пример теста с выводом логов", () -> {
            given()
                    .log().all()
//                .log().uri()
//                .log().body()
                    .get("/BookStore/v1/Books")
                    .then()
                    .log().all()
                    .body("books", hasSize(greaterThan(0)));
        });
    }

    @Test
    @Tag("API")
    void autirizeTest() {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        step("Генерируем токен /Account/v1/GenerateToken", () -> {
            given()
                    .contentType(ContentType.JSON)
                    .body(data) //для корректного вывода нужен toString()
                    .post("/Account/v1/GenerateToken")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("status", is("Success"))
                    .body("result", is("User authorized successfully."));
        });
    }

}
