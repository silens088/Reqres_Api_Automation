package tests.reqres;

import TestBase.Specification.ApiRequestSpecification;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import lombok_models.reqres.*;
import lombok_models.reqres.ForSingleResource.Response.SingleResourceMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//здесь используем lombok + restassured.specification + allure.restassured_filter

@Owner("velichko")
@Story("https://reqres.in")
@Feature("https://reqres.in")

public class ReqresTests_lombok extends ApiRequestSpecification {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @Tag("POST")
    @DisplayName("Проверяем успешный логин")
    void successfullLogin() {

        ForLoginRequest forLogin = new ForLoginRequest(); //создали обьект модели lombok
        forLogin.setEmail("eve.holt@reqres.in");
        forLogin.setPassword("cityslicka");

        step("проверяем успешный логин", () -> {
            ForLoginResponse loginResponse =
                    given()
                            .spec(requestReqresSpec)
                            .body(forLogin)
                            .when()
                            .post("/api/login")
                            .then()
                            .statusCode(200)
                            .extract().as(ForLoginResponse.class);

            assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Проверяем ошибку, если пароль не введен")
    void unSuccessfullLogin() {

        ForLoginRequest forLogin = new ForLoginRequest();
        forLogin.setEmail("eve.holt@reqres.in");

        step("Проверяем ошибку, что забыли ввести пароль", () -> {
            ForLoginResponse loginResponse =
                    given()
                            .spec(requestReqresSpec)
                            .body(forLogin)
                            .when()
                            .post("/api/login")
                            .then()
                            .statusCode(400)
                            .extract().as(ForLoginResponse.class);

            assertEquals("Missing password", loginResponse.getError());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Успешная регистрация пользователя")
    void successfulRegistrationUser() {

        ForRegistrationUserRequest request = new ForRegistrationUserRequest();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        step("Регистрируем пользователя", () -> {
            ForRegistrationUserResponse response =
                    given()
                            .spec(requestReqresSpec)
                            .body(request)
                            .when()
                            .post("/api/register")
                            .then()
                            .statusCode(200)
                            .extract().as(ForRegistrationUserResponse.class);

            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Не успешная регистрация пользователя")
    void usSuccessfulRegistrationUser() {

        ForRegistrationUserRequest request = new ForRegistrationUserRequest();
        request.setEmail("eve.holt@reqres.in");

        String expectedError = "Missing password";

        step("Регистрируем пользователя", () -> {
            ForRegistrationUserResponse response =
                    given()
                            .spec(requestReqresSpec)
                            .body(request)
                            .when()
                            .post("/api/register")
                            .then()
                            .statusCode(400)
                            .extract().as(ForRegistrationUserResponse.class);

            assertEquals(expectedError, response.getError());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Cоздаем пользователя morpheus")
    void postCreateUser() {
        ForUsersCreateRequest createRequest = new ForUsersCreateRequest();
        createRequest.setJob("leader");
        createRequest.setName("morpheus");

        String expectedName = "morpheus";
        String expectedJob = "leader";

        step("Проверяем создание пользователя", () -> {
            ForUsersCreateResponse createResponse =
                    given()
                            .spec(requestReqresSpec)
                            .body(createRequest)
                            .when()
                            .post("/api/users")
                            .then()
                            .statusCode(201)
                            .extract().as(ForUsersCreateResponse.class);

            assertEquals(expectedName, createResponse.getName());
            assertEquals(expectedJob, createResponse.getJob());
            assertNotNull(createResponse.getId());
            assertNotNull(createResponse.getCreatedAt());
            System.out.println(createResponse);
        });
    }

    //упрощенно
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

    //(C) жир
    @Test
    @Tag("GET")
    @DisplayName("Получаем ресурсы, данные через GET")
    void singleResource() {

        int expectedId = 2;
        String expectedName = "fuchsia rose";
        int expectedYear = 2001;
        String expectedColor = "#C74375";
        String expectedPantoneValue = "17-2031";
        String expectedUrl = "https://reqres.in/#support-heading";
        String expectedText = "To keep ReqRes free, contributions towards server costs are appreciated!";

        step("Проверяем получение ресурсов", () -> {
            SingleResourceMain singleResourceMain =
                    given()
                            .spec(requestReqresSpec)
                            .when()
                            .get("/api/unknown/2")
                            .then()
                            .spec(responseSpecification)
                            .statusCode(200)
                            .extract().as(SingleResourceMain.class);

            assertEquals(expectedId, singleResourceMain.getData().getId());
            assertEquals(expectedName, singleResourceMain.getData().getName());
            assertEquals(expectedYear, singleResourceMain.getData().getYear());
            assertEquals(expectedColor, singleResourceMain.getData().getColor());
            assertEquals(expectedPantoneValue, singleResourceMain.getData().getPantoneValue());
            assertEquals(expectedUrl, singleResourceMain.getSupport().getUrl());
            assertEquals(expectedText, singleResourceMain.getSupport().getText());
        });
    }

    //устаревший
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
                .statusCode(404)
                .body(is("{}"));
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
                .assertThat()
                .statusCode(200);
    }
}
