package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String setDate = generateDate(3);

    @Test
    public void shouldFillOutTheForm() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ижевск");
        $("[class='menu-item__control']").click();
        $(".calendar-input__custom-control").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Дулин-Фрунзе Иван");
        $("[data-test-id='phone'] input").setValue("+71234567890");
        $(".checkbox__box").click();
        $("[type='button'] [class=button__content]").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[class='notification__content']").shouldHave(text("Встреча успешно забронирована на " + setDate));
    }
}

