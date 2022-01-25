package test_base;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static filter.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

//спецификация нужна чтобы скрыть повторяющиеся действия и использовать их во множестве тестов с одинаковым эндпоинтом
//добавляем метод в given() и через .spec

public class BookStoreApiSpec {
    static public RequestSpecification requestSpecification = with()
            .baseUri("https://demoqa.com")
            .filter(customLogFilter().withCustomTemplates()) //шаблон аллюр
            .log().all()
            //.contentType("application/json")
            //.accept("application/json");
            .contentType(ContentType.JSON);

    static public ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
