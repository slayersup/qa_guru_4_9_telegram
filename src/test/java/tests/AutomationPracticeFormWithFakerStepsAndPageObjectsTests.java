package tests;

import org.junit.jupiter.api.Test;
import pages.StudentRegistrationPage;

public class AutomationPracticeFormWithFakerStepsAndPageObjectsTests extends TestBase{
    StudentRegistrationPage studentRegistrationPage;

    @Test
    void modalContentTest() {
        studentRegistrationPage = new StudentRegistrationPage();

        studentRegistrationPage.openPage();
        studentRegistrationPage.fillForm();
        studentRegistrationPage.checkData();
    }
}
