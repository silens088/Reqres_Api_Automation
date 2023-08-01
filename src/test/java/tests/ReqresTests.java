package tests;

import Specification.Specification;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import models.login.ForLoginRequest;
import models.login.ForLoginResponse;
import models.registration.ForRegistrationUserRequest;
import models.registration.ForRegistrationUserResponse;
import models.singleResource.SingleResourceMain;
import models.users.ForUsersCreateRequest;
import models.users.ForUsersCreateResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Owner("velichko")
@Story("https://reqres.in")
@Feature("https://reqres.in")

public class ReqresTests extends Specification {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @Tag("POST")
    @DisplayName("Авторизация пользователя")
    void successfullLogin() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        ForLoginRequest forLogin = new ForLoginRequest(); //создали обьект модели lombok
        forLogin.setEmail("eve.holt@reqres.in");
        forLogin.setPassword("cityslicka");

        step("проверяем успешный логин", () -> {
            ForLoginResponse loginResponse =
                    given()
                            .body(forLogin)
                            .when()
                            .post("/api/login")
                            .then()
                            .extract().as(ForLoginResponse.class);

            assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Проверяем ошибку, если пароль не введен")
    void unSuccessfullLogin() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK400());
        ForLoginRequest forLogin = new ForLoginRequest();
        forLogin.setEmail("eve.holt@reqres.in");

        step("Проверяем ошибку, что забыли ввести пароль", () -> {
            ForLoginResponse loginResponse =
                    given()
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
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        ForRegistrationUserRequest request = new ForRegistrationUserRequest();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        step("Регистрируем пользователя", () -> {
            ForRegistrationUserResponse response =
                    given()
                            .body(request)
                            .when()
                            .post("/api/register")
                            .then().log().body()
                            .extract().as(ForRegistrationUserResponse.class);

            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    @Tag("POST")
    @Description("В этом тесте мы не отправляем пароль и проверяем текст ответа")
    @DisplayName("Неуспешная регистрация пользователя")
    void usSuccessfulRegistrationUser() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK400());
        ForRegistrationUserRequest request = new ForRegistrationUserRequest();
        request.setEmail("eve.holt@reqres.in");
        String expectedError = "Missing password";

        step("Регистрируем пользователя", () -> {
            ForRegistrationUserResponse response =
                    given()
                            .body(request)
                            .when()
                            .post("/api/register")
                            .then().log().body()
                            .extract().as(ForRegistrationUserResponse.class);
            assertEquals(expectedError, response.getError());
        });
    }

    @Test
    @Tag("POST")
    @DisplayName("Cоздаем пользователя morpheus")
    void postCreateUser() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK201());
        ForUsersCreateRequest createRequest = new ForUsersCreateRequest();
        createRequest.setJob("leader");
        createRequest.setName("morpheus");

        String expectedName = "morpheus";
        String expectedJob = "leader";

        step("Проверяем создание пользователя", () -> {
            ForUsersCreateResponse createResponse =
                    given()
                            .body(createRequest)
                            .when()
                            .post("/api/users")
                            .then().log().body()
                            .extract().as(ForUsersCreateResponse.class);

            assertEquals(expectedName, createResponse.getName());
            assertEquals(expectedJob, createResponse.getJob());
            assertNotNull(createResponse.getId());
            assertNotNull(createResponse.getCreatedAt());
            System.out.println(createResponse);
        });
    }

    @Disabled
    @Test
    @Tag("POST")
    @Description("Демонстрация выключеного теста")
    @DisplayName("Проверяем ошибку, если пароль не введен")
    void negativeLogin() {

        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@tests.reqres.in");
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK400());
        given()
                .body(data)
                .when()
                .post("/api/login")
                .then().log().body()
                .body("error", is("Missing password"));
    }

    @Test
    @Description("Демонстрация упавшего теста")
    @DisplayName("Тест упал, неверный статус код")
    @Tag("POST")
    void negativeLoginNegative() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK201());
        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@tests.reqres.in");

        given()
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .body("error", is("Missing password"));
    }

    @Test
    @Tag("GET")
    @Description("Проверяем что полученные данные, соответствуют ожидаемым")
    @DisplayName("Получаем ресурсы, данные через GET")
    void singleResource() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());

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
                            .when()
                            .get("/api/unknown/2")
                            .then()
                            .spec(responseSpecification)
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

    @Test
    @Tag("GET")
    @DisplayName("Проверяем содержимое поля support.text")
    void getSingleUsers() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        given();
        String response =
                get("/api/users/2")
                        .then()
                        .extract().path("support.text");
        assertThat(response).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    @Tag("GET")
    @DisplayName("Проверяем что пользователь не найден")
    void singleUserNotFoundTest() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK404());
        given()
                .when()
                .get("/api/users/23")
                .then()
                .body(is("{}"));
    }

    @Test
    @Tag("GET")
    @DisplayName("Проверяем кол-во страниц и номер текущей страницы")
    void getListUsers() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        given()
                .when()
                .get("/api/users?page=2")
                .then().log().body()
                .body("page", is(2))
                .body("total_pages", is(2));
    }

    //тест позволяет с помощью регекса, найти и проверить конкретные данные в ответе:
    @Test
    @Tag("GET")
    @Description("Экспериментальный тест с Groovy + regex")
    @DisplayName("Ищем все имена содержащие ue")
    public void checkNameInListResource() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        step("Проверяем что LIST <RESOURCE> содержит в середине имени ue  ", () -> {
            given()
                    .when()
                    .get("/api/unknown")
                    .then().log().all()
                    .body("data.findAll{it.name =~/ue/}.name.flatten()", //ищем имена
                            hasItems("true red", "blue turquoise"))
                    .and()
                    .body("data.findAll{it.year =~/200/}.year.flatten()", //ищем года с 200
                            hasItems(2001, 2003));
        });
    }

    //example
    @Test
    @Tag("GET")
    @Description("Экспериментальный тест с Groovy + regex")
    @DisplayName("Ищем все email оканчивающиеся на @reqres.in и проверяем наличие в списке eve.holt@reqres.in")
    public void checkNameInListResource2() {
        Specification.InstallSpecification(Specification.requestSpec(baseURI), Specification.responseSpecOK200());
        step("Проверяем что LIST USERS стр1 - содержит емейл eve.holt@reqres.in", () -> {
            given()
                    .when()
                    .get("/api/users?page=1")
                    .then().log().all()
                    //найти все емейлы - которые неважно с чего начинаются - но заканчивается reqres.in
                    //- он собирает весь список всех емейлов: findAll{it.email =~/.*?@reqres.in/}
                    //- находит поле .email - flatten()
                    //далее в получившемся списке находим hasItem - наш емейл: eve.holt@reqres.in
                    .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                            hasItem("eve.holt@reqres.in"));
        });
    }
}