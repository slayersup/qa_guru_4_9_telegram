package tests;

import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static pages.StudentRegistrationPage.selectDayFromDataPicker;
import static utils.RandomUtils.getRandomBYearMap;
import static utils.RandomUtils.getRandomGender;

public class AutomationPracticeFormWithFakerLambdaTests extends TestBase {

    @Test
    void modalContentTest() {
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

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill in students registration form", () -> {
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(email).pressEnter();
            $("#genterWrapper").find(byText(gender)).click();
            $("#userNumber").setValue(userNumber);

            // Filling calendar block
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption(monthOfBirth);
            $(".react-datepicker__year-select").selectOption(yearOfBirth);
            selectDayFromDataPicker(dayOfBirth);

            $("#subjectsInput").setValue(englishSubject).pressEnter();
            $("#subjectsInput").setValue(mathsSubject).pressEnter();
            $("#hobbiesWrapper").find(byText(sportsHobby)).click();
            $("#hobbiesWrapper").find(byText(readingHobby)).click();
            $("#uploadPicture").uploadFromClasspath("images/" + imageFileName);
            $("#currentAddress").setValue(currentAddress);
            $("#react-select-3-input").setValue(state).pressEnter();
            $("#react-select-4-input").setValue(city).pressEnter();
            $("#submit").click();
        });

        step("Verify Submit Form", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

            ElementsCollection submitTableRows = $$(".table-responsive tr");
            submitTableRows.filterBy(text("Student Name")).shouldHave(texts(firstName + " " + lastName));
            submitTableRows.filterBy(text("Student Email")).shouldHave(texts(email));
            submitTableRows.filterBy(text("Gender")).shouldHave(texts(gender));
            submitTableRows.filterBy(text("Mobile")).shouldHave(texts(userNumber));
            submitTableRows.filterBy(text("Date of Birth"))
                    .shouldHave(texts(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
            submitTableRows.filterBy(text("Subjects")).shouldHave(texts(englishSubject + ", " + mathsSubject));
            submitTableRows.filterBy(text("Hobbies")).shouldHave(texts(sportsHobby + ", " + readingHobby));
            submitTableRows.filterBy(text("Picture")).shouldHave(texts(imageFileName));
            submitTableRows.filterBy(text("Address")).shouldHave(texts(currentAddress));
            submitTableRows.filterBy(text("State and City")).shouldHave(texts(state + " " + city));
        });
    }
}
