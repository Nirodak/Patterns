package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate() {
        int date = faker.number().numberBetween(3,15);
        return LocalDate.now().plusDays(date).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        String[] City = {"Краснодар", "Великий Новгород", "Екатеринбург", "Калининград", "Нижний Новгород",};
        Random random = new Random();
        int randomCity = random.nextInt(City.length);
        return City[randomCity];
    }

    public static String generateName(String locale) {
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        return faker.phoneNumber().cellPhone();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {

            return new UserInfo(generateCity("ru"), generateName("ru"), generatePhone("ru"));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
