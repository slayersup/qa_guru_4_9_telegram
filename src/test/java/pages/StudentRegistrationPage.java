package pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.util.HashMap;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.RandomUtils.getRandomBYearMap;
import static utils.RandomUtils.getRandomGender;

public class StudentRegistrationPage {
    Faker faker = new Faker();
    HashMap<String, String> randomDayMonthYearMap = getRandomBYearMap();

    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            gender = getRandomGender(),
            userNumber = faker.number().digits(10),
            yearOfBirth = randomDayMonthYearMap.get("year"),
            monthOfBirth = randomDayMonthYearMap.get("month"),
            dayOfBirth = randomDayMonthYearMap.get("day"),
            englishSubject = "English",
            mathsSubject = "Maths",
            sportsHobby = "Sports",
            readingHobby = "Reading",
            imageFileName = "image.png",
            currentAddress = "Street Lenin, 1",
            state = "Haryana",
            city = "Panipat";

    @Step("Open students registration form")
    public void openPage() {
        open("https://demoqa.com/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
    }

    @Step("Fill students registration form")
    public void fillForm() {
        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").val(userNumber);
        // set date
        setBirthDate(yearOfBirth, monthOfBirth, dayOfBirth);
        // set subject
        $("#subjectsInput").val(englishSubject);
        $(".subjects-auto-complete__menu-list").$(byText(englishSubject)).click();
        $("#subjectsInput").val(mathsSubject);
        $(".subjects-auto-complete__menu-list").$(byText(mathsSubject)).click();
        // set hobbies
        $("#hobbiesWrapper").$(byText(sportsHobby)).click();
        $("#hobbiesWrapper").$(byText(readingHobby)).click();
        // upload image
        $("#uploadPicture").uploadFromClasspath("images/" + imageFileName);
        // set current address
        $("#currentAddress").val(currentAddress);
        // set state and city
        $("#react-select-3-input").setValue(state).pressEnter();
        $("#react-select-4-input").setValue(city).pressEnter();

        $("#submit").click();
    }

    @Step("Set date of birth")
    public void setBirthDate(String year, String month, String day) {
        $("#dateOfBirthInput").clear();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        selectDayFromDataPicker(day);
    }

    @Step("Verify successful form submit")
    public void checkData() {
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
        $x("//td[text()='Student Email']").parent().shouldHave(text(email));
        $x("//td[text()='Gender']").parent().shouldHave(text(gender));
        $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
        $x("//td[text()='Date of Birth']").parent().shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
        $x("//td[text()='Subjects']").parent().shouldHave(text(englishSubject + ", " + mathsSubject));
        $x("//td[text()='Hobbies']").parent().shouldHave(text(sportsHobby + ", " + readingHobby));
        $x("//td[text()='Picture']").parent().shouldHave(text(imageFileName));
        $x("//td[text()='Address']").parent().shouldHave(text(currentAddress));
        $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
    }

    public static void selectDayFromDataPicker(String dayNumber) {
        if (Integer.parseInt(dayNumber) < 15) {
            $$(".react-datepicker__day--0" + dayNumber).first().click();
        } else {
            $$(".react-datepicker__day--0" + dayNumber).last().click();
        }
    }
}