package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test_base.BookStoreApiSpec;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

//АПИ тесты на https://demoqa.com
//добавлен шаблон отчета аллюр customLogFilter().withCustomTemplates()
//Добавлена спецификация BookStoreApiSpec в тесты

public class BookStoreApiTests_Spec extends BookStoreApiSpec {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    @Tag("GET")
    @DisplayName("Получить список книг + спецификация")
    void getListBooks() {
        given(requestSpecification)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification)
                .body("books", hasSize(greaterThan(0)));
    }

    String isbn = "9781449325862";
    String title = "Git Pocket Guide";
    String subTitle = "A Working Introduction";
    String author = "Richard E. Silverman";

    @Test
    @Tag("GET")
    @DisplayName("Получить книгу и проверить поля + спецификация")
    void getOneBook() {
        given(requestSpecification)
                .get("https://demoqa.com/BookStore/v1/Book?ISBN=9781449325862")
                .then()
                .spec(responseSpecification)
                .body("isbn", is(isbn))
                .body("title", is(title))
                .body("subTitle", is(subTitle))
                .body("author", is(author))
                .log().all();
    }


    @Test
    @Tag("POST")
    @DisplayName("Тест на авторизацию + спецификация")
    void authorizeTest() {

        Map<String, String> userLoginData = new HashMap<>();
        userLoginData.put("userName", "alex");
        userLoginData.put("password", "asdsad#frew_DFS2");

        given()
                .spec(requestSpecification)
                .body(userLoginData)
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpecification)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

}
