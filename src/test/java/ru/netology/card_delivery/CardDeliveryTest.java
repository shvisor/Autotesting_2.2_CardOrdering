package ru.netology.card_delivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    /**
     * С помощью метода рассчитываем дату в формате dd.MM.yyyy
     *
     * @param gap количество дней прибавляемое к текущей дате
     * @return возвращаем полученную дату в заданном формате
     */
    public String setDate(int gap) {
        SimpleDateFormat onlyDate = new SimpleDateFormat("dd.MM.yyyy"); // задаем формат даты
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, gap);
        date = calendar.getTime();

        return onlyDate.format(date);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPositiveTestWithDefaultDateTask_1() {
        int gap = 3;

        $("[data-test-id='city'] input").setValue("Кемерово");
        $("[data-test-id='name'] input").setValue("Ямщиков Максим");
        $("[data-test-id='phone'] input").setValue("+79651234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
    }

    @Test
    void shouldPositiveTestWithInputDateTask_1() {
        int gap = 5;

        $("[data-test-id='city'] input").setValue("Кемерово");
        $("[data-test-id='name'] input").setValue("Ямщиков Максим");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap));
        $("[data-test-id='phone'] input").setValue("+79651234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
    }

//    @Test
//    void shouldPositiveTestTask_2() {
//        int gap = 7; // задаем временной гэп с текущей датой
//
//        $("[data-test-id='city'] input").val("Ке");
//        $(withText("Кемерово")).click();
//        $("button .icon_name_calendar").click();
//
//        $("[data-test-id='name'] input").val("Ямщиков Максим");
//        $("[data-test-id='phone'] input").val("+79651234567");
//        $("[data-test-id='agreement']").click();
//        $(byText("Забронировать")).click();
//        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
//        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + setDate(gap)));
//    }
}
