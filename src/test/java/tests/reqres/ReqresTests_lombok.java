package tests.reqres;

import TestBase.Specification.ApiRequestSpecification;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import lombok_models.reqres.ForLoginRequest;
import lombok_models.reqres.ForLoginResponse;
import lombok_models.reqres.OneModelTest;
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
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Проверяем что забыли ввести пароль")
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

    //добавить тесты на регистрацию успешную и нет

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
