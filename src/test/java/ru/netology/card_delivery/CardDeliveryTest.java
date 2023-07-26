package ru.netology.card_delivery;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    /**
     * С помощью метода получаем системную дату в формате dd.MM.yyyy
     *
     * @param gap количество дней прибавляемое к текущей дате
     * @return возвращаем полученную дату в заданном формате
     */
    public String setDate(int gap) {
        return LocalDate.now().plusDays(gap).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public long convert(int gap) {
        return LocalDate.now().plusDays(gap).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPositiveTestWithDefaultDateTask() {
        int gap = 3;

        $("[data-test-id='city'] input").val("Кемерово");
        $("[data-test-id='name'] input").val("Ямщиков Максим");
        $("[data-test-id='phone'] input").val("+79651234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
    }

    @Test
    void shouldPositiveTestWithInputDateTask() {
        int gap = 5;

        $("[data-test-id='city'] input").val("Кемерово");
        $("[data-test-id='name'] input").val("Ямщиков Максим");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap));
        $("[data-test-id='phone'] input").val("+79651234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
    }

    @Test
    void shouldPositiveTestDropDawnTask() {
        int gap = 7;

        $("[data-test-id='city'] input").val("Ке");
        $(withText("Кемерово")).click();
        $("button .icon_name_calendar").click();
        if (LocalDate.now().getMonthValue() != LocalDate.now().plusDays(gap).getMonthValue()) {
            $("[data-step='1']").click();
        }
        $("[data-day='" + convert(gap) + "']").click();
        $("[data-test-id='name'] input").val("Ямщиков Максим");
        $("[data-test-id='phone'] input").val("+79651234567");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
    }
}
