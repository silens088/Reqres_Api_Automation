package filter;

//1. Этот фильтра расширяет наши логи (AllureListener) в более красивый вид. у19 - 1.05
//2. Мы создаем в resources - folder ftl - и создать 2 файла: request.ftl | response.ftl (копипастим)
//3. Получаем красивый лог в аллюр отчете.
//используем без изменений

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

