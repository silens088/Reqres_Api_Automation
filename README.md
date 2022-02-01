
## API automation project for [reqres.in](https://reqres.in/)

### Technology used:

| Java | Rest Assured | Gradle | Lombok | Junit5 | Jenkins |
|:------:|:----:|:------:|:------:|:------:|:--------:|
| ![Java](src/images/icons/Java.png) | ![Rest-Assured](src/images/icons/Rest-Assured.png) | ![Gradle](src/images/icons/Gradle.png) | ![Lombok](src/images/icons/Lombok.png) | ![JUnit5](src/images/icons/JUnit5.png) | ![Jenkins](src/images/icons/Jenkins.png) |

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


Project contains Rest Assured for https://reqres.in/ test site
Проект покрывает тестами следующий функционал:
- 


# Allure TestOps

Click <a target="_blank" href="https://allure.autotests.cloud/project/644/dashboards">here</a> to see a list of automated test cases.

Here are the test cases: <br />
<img width="749" alt="Allure-Test-cases" src="https://user-images.githubusercontent.com/32490159/138930923-a891798c-cfec-4783-aaa7-d718b49d43b6.png">

And this is the test launch overview - all 7 tests passed, yay 😺
<img width="752" alt="Allure-launch-overview" src="https://user-images.githubusercontent.com/32490159/138930942-879bd942-fe42-4e79-9c72-9444d46c8ea9.png">



## Run tests - Jenkins job
Jenkins is a tool from where you can run the tests.
Click <a target="_blank" href="https://jenkins.autotests.cloud/job/009-Vitalii-Reqres_Api_Automation/">here</a> to do that.
<img width="1068" alt="Jenkins" src="https://user-images.githubusercontent.com/32490159/138930976-c4778720-9328-4f5c-9f19-130ec1e51a6b.png">

### Run locally

If you've downloaded these tests to your computer, run them with the following command:
```bash
gradle clean test
```


### Allure report

Each test has request and response attached

![allure report](./images/AllureReport.png)

### Response body

![allure report](./images/ResponseBody.png)