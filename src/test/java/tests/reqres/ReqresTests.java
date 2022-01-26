package tests.reqres;

import TestBase.Specification.ApiRequestSpecification;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@Owner("velichko")
@Story("https://reqres.in")
@Feature("https://reqres.in")

public class ReqresTests extends ApiRequestSpecification {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @Tag("POST")
    @DisplayName("Проверяем успешный логин")
    void successfullLogin() {

        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@tests.reqres.in");
        data.put("password", "cityslicka");

        given()
                .spec(requestReqresSpec)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(201);
    }

    @Test
    @Tag("POST")
    @DisplayName("Проверяем ошибку, если пароль не введен")
    void negativeLogin() {

        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@tests.reqres.in");

        given()
                .spec(requestReqresSpec)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @Tag("GET")
    @DisplayName("Проверяем содержимое поля support.text")
    void getSingleUsers() {
        given()
                .spec(requestReqresSpec);
        String response =
                get("/api/users/2")
                        .then()
                        .statusCode(200)
                        .extract().path("support.text");

        assertThat(response).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    @Tag("GET")
    @DisplayName("Проверяем что пользователь не найден")
    void singleUserNotFoundTest() {

        given()
                .spec(requestReqresSpec)
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @Tag("GET")
    @DisplayName("Проверяем кол-во страниц и текущую страницу")
    void getListUsers() {

        given()
                .spec(requestReqresSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(responseSpecification)
                .body("page", is(2))
                .body("total_pages", is(2))
                .assertThat().statusCode(200);
    }

    @Test
    @Tag("POST")
    @DisplayName("создаем пользователя morpheus")
    void postCreateUser() {

        Map<String, String> data = new HashMap<>();
        data.put("name", "morpheus");
        data.put("job", "leader");

        given()
                .spec(requestReqresSpec)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue());
    }
}
