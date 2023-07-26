package com.demo.profile.main.model.profile;

import com.demo.profile.main.model.Friendship;
import com.demo.profile.main.repository.FriendshipRepository;
import com.demo.profile.main.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.demo.profile.main.util.ProfileDataGenerator.generateRandomProfile;

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
    CommandLineRunner commandLineRunner(ProfileRepository repository, FriendshipRepository friendshipRepository) {
        return args -> {

            // Create profiles
            List<Profile> profiles = createProfiles(profilesTotal);
            repository.saveAll(profiles);

            // Create friends connections
            List<Friendship> friendships = createFriendsConnections(profiles, friendsTotal);
            friendshipRepository.saveAll(friendships);
        };
    }

    private List<Profile> createProfiles(int profilesTotal) {
        List<Profile> profiles = new ArrayList<>();

        for (int i = 0; i < profilesTotal; i++) {
            Profile profile = generateRandomProfile();
            profiles.add(profile);
        }

        return profiles;
    }

    private List<Friendship> createFriendsConnections(List<Profile> profiles, int friendsTotal) {
        Random random = new Random();
        List<Friendship> friendships = new ArrayList<>();

        for (Profile profile : profiles) {
            List<Profile> friends = new ArrayList<>(profiles);
            friends.remove(profile);

            for (int i = 0; i < friendsTotal; i++) {
                int randomIndex = random.nextInt(friends.size());

                Friendship friendship = new Friendship(profile.getId(), randomIndex);
                friendships.add(friendship);
            }
        }
        return friendships;
    }

}
