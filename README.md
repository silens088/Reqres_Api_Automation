
## API automation project for [reqres.in](https://reqres.in/)
Project contains Api tests for https://reqres.in/ site.
These are the tests which check these handlers' functionality.


## Technology used:

| Java | Rest Assured | Gradle | Lombok | Junit5 | Jenkins | Telegram |
|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|
| ![Java](src/images/icons/Java.png) | ![Rest-Assured](src/images/icons/Rest-Assured.png) | ![Gradle](src/images/icons/Gradle.png) | ![Lombok](src/images/icons/Lombok.png) | ![JUnit5](src/images/icons/JUnit5.png) | ![Jenkins](src/images/icons/Jenkins.png) | ![Telegram](src/images/icons/Telegram.png) |

[comment]: <> (## Используемые технологии и инструменты)

[comment]: <> (![Intelij_IDEA]&#40;src/images/icons/Intelij_IDEA.png&#41;)

[comment]: <> (![Java]&#40;src/images/icons/Java.png&#41;)

[comment]: <> (![Selenide]&#40;src/images/icons/Selenide.png&#41;)

[comment]: <> (![Selenoid]&#40;src/images/icons/Selenoid.png&#41;)

[comment]: <> (![Gradle]&#40;src/images/icons/Gradle.png&#41;)

[comment]: <> (![JUnit5]&#40;src/images/icons/JUnit5.png&#41;)

[comment]: <> (![Allure Report]&#40;src/images/icons/Allure_Report.png&#41;)

[comment]: <> (![AllureTestOps]&#40;src/images/icons/AllureTestOps.png&#41;)

[comment]: <> (![Github]&#40;src/images/icons/Github.png&#41;)

[comment]: <> (![Jenkins]&#40;src/images/icons/Jenkins.png&#41;)

[comment]: <> (![Rest-Assured]&#40;src/images/icons/Rest-Assured.png&#41;)

[comment]: <> (![Telegram]&#40;src/images/icons/Telegram.png&#41;)

[comment]: <> (![Jira]&#40;src/images/icons/Jira.png&#41;)

[comment]: <> (![Lombok]&#40;src/images/icons/Lombok.png&#41;)



## Allure TestOps

Click <a target="_blank" href="https://allure.autotests.cloud/project/948/dashboards">here</a> to see a list of automated test cases.

Here are the test cases: 

![](src/images/reports/AllureTestOps001.png)

Test launch overview:

![](src/images/reports/AllureTestOps002.png)



## Run tests - Jenkins job

Jenkins is a tool from where you can run the tests. 
Click <a target="_blank" href="https://jenkins.autotests.cloud/job/009-Vitalii-ReqresApi/">here</a> to do that.

![](src/images/reports/Jenkins001.png)

### Run locally

If you've downloaded these tests to your computer, run them with the following command:
```bash
gradle clean test
```


### Allure report

Each test has request and response attached

![allure report](src/images/reports/AllureReport001.png)


### Telegram report

Each test builds sent report into telegram bot

![](src/images/reports/TelegramReport001.png)



