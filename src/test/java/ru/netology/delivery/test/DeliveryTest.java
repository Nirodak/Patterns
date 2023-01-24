package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.*;


class DeliveryTest {

    SelenideElement element;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    private void enterValue(String locale) {

        $("[data-test-id='city'] input").setValue(generateCity(locale));
        $("[data-test-id='city'] input").sendKeys(Keys.chord(Keys.ESCAPE));
        $("[data-test-id='name'] input").setValue(generateName(locale));
        $("[data-test-id='phone'] input").setValue(generatePhone(locale));

    }

    public void clicker() {
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndRePlanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = generateDate();
        var secondMeetingDate = generateDate();
        while (firstMeetingDate == secondMeetingDate) {
            secondMeetingDate = generateDate();
        }
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, firstMeetingDate,Keys.ESCAPE);
        enterValue("ru");
        clicker();
        $(".notification__content").shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, secondMeetingDate, Keys.ESCAPE);
        $$("button").find(exactText("Запланировать")).click();
        $$("button").find(exactText("Перепланировать")).click();
        $(".notification__content").shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
        Configuration.holdBrowserOpen=true;

        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

    }
}
