package ru.netology.card_delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryNegativeTest {

    /**
     * С помощью метода получаем системную дату в формате dd.MM.yyyy
     *
     * @param gap количество дней прибавляемое к текущей дате
     * @return возвращаем полученную дату в заданном формате
     */
    public String setDate(int gap, String pattern) {
        return LocalDate.now().plusDays(gap).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvFileSource (files = "src/test/resources/cityTestData.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldCityTest(String testName, String city, String name, String phone, String fail) {
        int gap = 5;
        String pattern = "dd.MM.yyyy";

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap, pattern));
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource (files = "src/test/resources/nameTestData.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldNameTest(String testName, String city, String name, String phone, String fail) {
        int gap = 5;
        String pattern = "dd.MM.yyyy";

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap, pattern));
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource (files = "src/test/resources/phoneTestData.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldPhoneTest(String testName, String city, String name, String phone, String fail) {
        int gap = 5;
        String pattern = "dd.MM.yyyy";

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap, pattern));
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__inner .input__sub").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource (files = "src/test/resources/dateTestData.csv", numLinesToSkip = 1, delimiter = '|')
    void shouldDateTest(String testName, String city, String name, String phone, int lag, String dateFormat, String fail) {
        int gap = lag;
        String pattern = dateFormat;

        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap, pattern));
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText(fail));
    }

    @Test
    void shouldDateNotSelected() {

        $("[data-test-id='city'] input").val("Новосибирск");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='name'] input").val("Ямщиков Максим");
        $("[data-test-id='phone'] input").val("+79651234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Неверно введена дата"));
    }

    @Test
    void shouldWithoutCheckbox() {
        int gap = 5;
        String pattern = "dd.MM.yyyy";

        $("[data-test-id='city'] input").val("Новосибирск");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(setDate(gap, pattern));
        $("[data-test-id='name'] input").val("Ямщиков Максим");
        $("[data-test-id='phone'] input").val("+79651234567");
        $(".button").click();
        $("[data-test-id='agreement']").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
