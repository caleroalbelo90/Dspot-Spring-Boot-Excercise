package com.dspot.profile.main.util;

import com.dspot.profile.main.model.profile.Profile;
import com.github.javafaker.Faker;

public class ProfileDataGenerator {
    private static final Faker faker = new Faker();;

    public static Profile generateRandomProfile() {
        String img = faker.internet().image();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.phoneNumber().phoneNumber();
        String address = faker.address().fullAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipcode = faker.address().zipCode();
        boolean available = faker.bool().bool();

        return new Profile(img, firstName, lastName, phone, address, city, state, zipcode, available);
    }

}