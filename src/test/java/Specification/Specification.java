package Specification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static filter.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

//спецификация нужна чтобы скрыть повторяющиеся действия и использовать их во множестве тестов с одинаковым эндпоинтом
//добавляем в нужные методы через: given() || .spec

public class Specification {

//    public static RequestSpecification requestReqresSpec = with()
//            .baseUri("https://reqres.in")
//            .filter(customLogFilter().withCustomTemplates())
//            .log().all()
//            .contentType(ContentType.JSON);
//
//    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
//            .expectStatusCode(200)
//            .build();


    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpecOK201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification responseSpecOK400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification responseSpecOK404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static void InstallSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
