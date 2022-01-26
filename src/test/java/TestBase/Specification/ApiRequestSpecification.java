package TestBase.Specification;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static filter.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

//спецификация нужна чтобы скрыть повторяющиеся действия и использовать их во множестве тестов с одинаковым эндпоинтом
//добавляем в нужные методы через: given() || .spec

public class ApiRequestSpecification {

    public static RequestSpecification requestDemoqaSpec = with()
            .baseUri("https://demoqa.com")
            .filter(customLogFilter().withCustomTemplates()) //шаблон аллюр (фильтр, шаблон tpl)
            .log().all()
            .contentType(ContentType.JSON);
            //.contentType("application/json")
            //.accept("application/json");

    public static RequestSpecification requestReqresSpec = with()
            .baseUri("https://reqres.in")
            //.basePath("/api")
            .filter(customLogFilter().withCustomTemplates())
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
