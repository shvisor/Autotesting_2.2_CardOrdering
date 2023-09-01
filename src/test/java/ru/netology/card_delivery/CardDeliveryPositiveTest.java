package ru.netology.card_delivery;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Keys;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryPositiveTest {

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
//        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/data.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldPositiveTestWithDefaultDateTask(String city, String name, String phone, String expectedSuccess, String expectedMeeting) {
        int defaultGap = 3;

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedSuccess));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedMeeting + setDate(defaultGap)));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/data.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldPositiveTestWithInputDateTask(String city, String name, String phone, String expectedSuccess, String expectedMeeting) {
        int gap = 5;

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap));
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedSuccess));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedMeeting + setDate(gap)));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/data.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldPositiveTestDropDawnTask(String city, String name, String phone, String expectedSuccess, String expectedMeeting) {
        int gap = 7;

        $("[data-test-id='city'] input").val("ол");
        $(withText(city)).click();
        $("button .icon_name_calendar").click();
        if (LocalDate.now().getMonthValue() != LocalDate.now().plusDays(gap).getMonthValue()) {
            $("[data-step='1']").click();
        }
        $("[data-day='" + convert(gap) + "']").click();
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedSuccess));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(expectedMeeting + setDate(gap)));
    }
}
