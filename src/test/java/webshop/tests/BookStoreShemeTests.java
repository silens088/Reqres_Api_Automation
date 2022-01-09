package webshop.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

//в этом тесте мы проверяем JSON схему.
//1. Добавим либу "io.rest-assured:json-schema-validator:4.3.1"
//2. в resourses - создаем папку shemes - создаем файл с структурой JSON схемы из response которую конвертнули например из https://www.jsonschema.net/"
//3. в body импортим спец метод на схему и указываем путь к нашей схеме (.body(matchesJsonSchemaInClasspath("shemes/GenerateTokenSheme.json")))

public class BookStoreShemeTests {

    @Test
    void authorizeWithShemeTest() {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body(matchesJsonSchemaInClasspath("shemes/GenerateTokenSheme.json"))
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }
}
