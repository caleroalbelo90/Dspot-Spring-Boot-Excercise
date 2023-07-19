package com.demo.profile.main.model;

import com.demo.profile.main.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProfileConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository repository) {
        return args -> {
            Profile profile1 = new Profile("img1.jpg", "John", "Doe", "1234567890", "123 Main St", "City1", "State1", "12345", true);
            Profile profile2 = new Profile("img2.jpg", "Jane", "Smith", "9876543210", "456 Elm St", "City2", "State2", "54321", false);
            Profile profile3 = new Profile("img3.jpg", "Michael", "Johnson", "5555555555", "789 Oak St", "City3", "State3", "67890", true);
            Profile profile4 = new Profile("img4.jpg", "Emily", "Brown", "1112223333", "987 Pine St", "City4", "State4", "13579", false);
            Profile profile5 = new Profile("img5.jpg", "David", "Williams", "4444444444", "321 Maple St", "City5", "State5", "86420", true);

            repository.saveAll(
                    List.of(profile1, profile2, profile3, profile4, profile5)
            );
        };
    }



}
