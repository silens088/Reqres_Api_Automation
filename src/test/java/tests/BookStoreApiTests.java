package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static filter.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

//АПИ тесты на https://demoqa.com
//добавлен базовый отчет аллюр
//добавлен красивый шаблон отчет аллюр

public class BookStoreApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    @DisplayName("Получить список книг")
    void getListBooks() {
        given(requestSpecification)
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .log().all()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void authorizeTest() {

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

    @Test
    @Tag("API")
    @DisplayName("Пример теста authorizeTest + добавили allureListener")
    void authorizeWithAllureTest() {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        step("Генерируем токен /Account/v1/GenerateToken", () -> {
            given()
                    .filter(new AllureRestAssured()) //эта штука расширяет наш отчет в аллюре.
                    .contentType(ContentType.JSON)
                    .body(data)
                    .post("/Account/v1/GenerateToken")
                    .then()
                    .log().body()
                    .log().all()
                    .statusCode(200)
                    .body("status", is("Success"))
                    .body("result", is("User authorized successfully."));
        });
    }

    @Test
    @Tag("API")
    @DisplayName("Пример теста authorizeTest + добавили allureListener + Красивый фильтр")
    void authorizeWithAllureAndLogFilterTest() {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        step("Генерируем токен /Account/v1/GenerateToken", () -> {
            given()
                    .filter(customLogFilter().withCustomTemplates()) //эта штука расширяет наш отчет в аллюре.
                    .contentType(ContentType.JSON)
                    .body(data)
                    .post("/Account/v1/GenerateToken")
                    .then()
                    .log().body()
                    .log().all()
                    .statusCode(200)
                    .body("status", is("Success"))
                    .body("result", is("User authorized successfully1."));
        });
    }
}
