package filter;

//1. Этот фильтра расширяет наши логи Allure, в более красивый вид.
//2. Мы создаем в resources - folder ftl - и создать 2 файла: request.ftl | response.ftl (структура)
//3. Добавляем в тесты после: given()  .filter(customLogFilter().withCustomTemplates())
//4. Получаем красивый лог в аллюр отчете.

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomLogFilter {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private CustomLogFilter() {
    }

    public static CustomLogFilter customLogFilter() {
        return InitLogFilter.logFilter;
    }

    public AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("response.ftl");
        return FILTER;
    }

    private static class InitLogFilter {
        private static final CustomLogFilter logFilter = new CustomLogFilter();
    }
}

