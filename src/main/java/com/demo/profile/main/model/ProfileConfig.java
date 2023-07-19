package com.demo.profile.main.model;

import com.demo.profile.main.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class ProfileConfig {

    private final int profilesTotal;
    private final int friendsTotal;

    @Autowired
    public ProfileConfig(@Value("${config.profilesTotal}") int profilesTotal,
                         @Value("${config.friendsTotal}") int friendsTotal) {
        this.profilesTotal = profilesTotal;
        this.friendsTotal = friendsTotal;
    }

    @Bean
    CommandLineRunner commandLineRunner(ProfileRepository repository) {
        return args -> {

            // Create profiles
            List<Profile> profiles = createProfiles(profilesTotal);
            repository.saveAll(profiles);

            // Create friends connections
            createFriendsConnections(profiles, friendsTotal, repository);

        };
    }

    private List<Profile> createProfiles(int profilesTotal) {
        List<Profile> profiles = new ArrayList<>();

        for (int i = 0; i < profilesTotal; i++) {
            //TODO improve the way to create random profiles
            Profile profile = new Profile("img1.jpg", "John", "Doe", "1234567890", "123 Main St", "City1", "State1", "12345", true);
            profiles.add(profile);
        }

        return profiles;
    }

    private void createFriendsConnections(List<Profile> profiles, int friendsTotal, ProfileRepository repository) {
        Random random = new Random();

        for (Profile profile : profiles) {
            List<Profile> friends = new ArrayList<>(profiles);
            friends.remove(profile);

            for (int i = 0; i < friendsTotal; i++) {
                int randomIndex = random.nextInt(friends.size());
                Profile friend = friends.get(randomIndex);
                profile.addFriend(friend);
                friends.remove(randomIndex);
            }
        }

        repository.saveAll(profiles);
    }



}
