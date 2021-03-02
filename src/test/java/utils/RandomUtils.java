package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class RandomUtils {

    public static String getRandomGender() {
        StringBuilder[] genders = new StringBuilder[3];
        genders[0] = new StringBuilder("Male");
        genders[1] = new StringBuilder("Female");
        genders[2] = new StringBuilder("Other");

        return genders[getRandomInt(0, 2)].toString();
    }

    public static int getRandomInt(final int min, final int max) {
        Random random = new Random();

        return random.nextInt((max - min) + 1) + min;
    }

    static String makeFirstUpperCase(String upperMonth) {
        if (upperMonth == null || upperMonth.isEmpty()) return "";

        return upperMonth.charAt(0) + upperMonth.substring(1).toLowerCase();
    }

    static LocalDate getRandomBirthDayToLocalDate() {
        Faker faker = new Faker();
        Date birthDay = faker.date().birthday(17, 50);

        return birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static HashMap<String, String> getRandomBYearMap() {
        LocalDate birthday = getRandomBirthDayToLocalDate();
        String generatedYear = birthday.getYear() + "";
        String generatedMonth = makeFirstUpperCase(birthday.getMonth() + "");
        int day = birthday.getDayOfMonth();
        String generatedDay;

        if (day < 10) {
            generatedDay = "0" + day;
        } else {
            generatedDay = day + "";
        }

        HashMap<String, String> dayMonthYear = new HashMap<>();
        dayMonthYear.put("day", generatedDay);
        dayMonthYear.put("month", generatedMonth);
        dayMonthYear.put("year", generatedYear);

        return dayMonthYear;
    }
}
