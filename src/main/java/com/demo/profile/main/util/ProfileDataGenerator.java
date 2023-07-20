package com.demo.profile.main.util;

import com.demo.profile.main.model.Profile;

import java.util.Random;

public class ProfileDataGenerator {

    private static final String[] FIRST_NAMES = {
            "John", "Emma", "Michael", "Olivia", "William", "Ava", "James", "Sophia", "Alexander", "Isabella",
            "Elijah", "Mia", "Benjamin", "Amelia", "Lucas", "Harper", "Mason", "Evelyn", "Logan", "Abigail",
            "Ethan", "Emily", "Oliver", "Elizabeth", "Jacob", "Sofia", "Daniel", "Ella", "Aiden", "Grace",
            "Jackson", "Chloe", "Matthew", "Victoria", "David", "Scarlett", "Joseph", "Zoey", "Samuel", "Lily",
            "Sebastian", "Hannah", "Carter", "Nora", "Wyatt", "Addison", "Jayden", "Lillian"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
            "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
            "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins"
    };

    private static final String[] CITIES = {
            "New York", "Los Angeles", "Chicago", "Houston", "Miami", "San Francisco", "Dallas", "Boston", "Seattle", "Denver",
            "Phoenix", "Atlanta", "Philadelphia", "San Diego", "Washington", "San Antonio", "Austin", "Charlotte", "Nashville", "Las Vegas",
            "Portland", "Orlando", "Tampa", "Minneapolis", "Detroit", "Kansas City", "Cleveland", "Raleigh", "Cincinnati", "St. Louis",
            "Indianapolis", "Milwaukee", "Pittsburgh", "Columbus", "San Jose", "Sacramento", "Salt Lake City", "San Juan", "Honolulu", "Albuquerque",
            "Omaha", "Memphis", "Louisville", "Richmond", "Birmingham", "Tulsa", "Fresno", "New Orleans", "El Paso", "Anchorage"
    };

    private static final String[] STATES = {
            "NY", "CA", "IL", "TX", "FL", "CA", "TX", "MA", "WA", "CO",
            "AZ", "GA", "PA", "CA", "DC", "TX", "TX", "NC", "TN", "NV",
            "OR", "FL", "FL", "MN", "MI", "MO", "OH", "NC", "OH", "MO",
            "IN", "WI", "PA", "OH", "CA", "CA", "UT", "PR", "HI", "NM",
            "NE", "TN", "KY", "VA", "AL", "OK", "CA", "LA", "TX", "AK"
    };

    private static final String[] ZIP_CODES = {
            "10001", "90001", "60601", "77001", "33101", "94101", "75201", "02101", "98101", "80201",
            "85001", "30301", "19101", "92101", "20001", "78201", "73301", "28201", "37201", "89101",
            "97201", "32801", "33601", "55401", "48201", "64101", "44101", "27601", "45201", "63101",
            "46201", "53201", "15201", "43201", "95101", "95801", "84101", "00901", "96801", "87101",
            "68101", "38101", "40201", "23201", "35201", "74101", "93701", "70112", "79901", "99501"
    };

    private static final Random random = new Random();

    public static Profile generateRandomProfile() {
        String img = "https://example.com/profile/" + random.nextInt(1000); // Generate random image URL
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String phone = generateRandomPhoneNumber();
        String address = random.nextInt(1000) + " " + LAST_NAMES[random.nextInt(LAST_NAMES.length)] + " St";
        String city = CITIES[random.nextInt(CITIES.length)];
        String state = STATES[random.nextInt(STATES.length)];
        String zipcode = ZIP_CODES[random.nextInt(ZIP_CODES.length)];
        boolean available = random.nextBoolean();

        return new Profile(img, firstName, lastName, phone, address, city, state, zipcode, available);
    }

    private static String generateRandomPhoneNumber() {
        StringBuilder phoneBuilder = new StringBuilder();
        phoneBuilder.append("(");
        for (int i = 0; i < 3; i++) {
            phoneBuilder.append(random.nextInt(10));
        }
        phoneBuilder.append(") ");
        for (int i = 0; i < 3; i++) {
            phoneBuilder.append(random.nextInt(10));
        }
        phoneBuilder.append("-");
        for (int i = 0; i < 4; i++) {
            phoneBuilder.append(random.nextInt(10));
        }
        return phoneBuilder.toString();
    }
}