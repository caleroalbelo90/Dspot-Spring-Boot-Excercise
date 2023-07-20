package com.demo.profile.main.service;

import com.demo.profile.main.model.Friendship;
import com.demo.profile.main.model.Profile;
import com.demo.profile.main.repository.FriendshipRepository;
import com.demo.profile.main.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, FriendshipRepository friendshipRepository) {
        this.profileRepository = profileRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfile(Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);

        if (profileOptional.isPresent()) {
            return profileOptional.get();
        }

        throw new IllegalStateException("Profile with id " + id + " does not exists");
    }


    /**
     * Get the full list of friends for a given profile
     *
     * @param profileId id of the profile to get the friends list
     */
    public List<Long> getFriends(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);

        if (profileOptional.isPresent()) {
             return profileRepository.getFriendsList(profileId);
        }

        throw new IllegalStateException("Profile with id " + profileId + " does not exists");
    }


    /**
     * Get the shortest connection between two profiles
     *
     * @param profileId1
     * @param profileId2
     */
    public List<Long> getShortestConnection(Long profileId1, Long profileId2) {

        // Verify both profiles are not the same
        if (Objects.equals(profileId1, profileId2)) {
            throw new IllegalStateException("Profile cannot be the same");
        }

        // Verify both profiles exist
        checkIfProfileExists(profileId1);
        checkIfProfileExists(profileId2);

        List<Long> shortestConnection = new ArrayList<>();

        Set<Long> visitedProfiles = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();
        Map<Long, Long> parentMap = new HashMap<>();

        visitedProfiles.add(profileId1);
        queue.add(profileId1);
        parentMap.put(profileId1, null);

        while (!queue.isEmpty()) {
            Long currentProfileId = queue.poll();

            if (currentProfileId.equals(profileId2)) {
                break; // We found the shortest connection
            }

            List<Long> friends = getFriendsList(currentProfileId);

            for (Long friendId : friends) {
                if (!visitedProfiles.contains(friendId)) {
                    visitedProfiles.add(friendId);
                    queue.add(friendId);
                    parentMap.put(friendId, currentProfileId);
                }
            }
        }

        if (parentMap.containsKey(profileId2)) {
            Long currentId = profileId2;
            while (currentId != null) {
                shortestConnection.add(0, currentId);
                currentId = parentMap.get(currentId);
            }
        }

        return shortestConnection;
    }

    /**
     * Check if the profile exists, or throw an exception if not found
     *
     * @param profileId The id of the profile to check
     */
    private void checkIfProfileExists(Long profileId) {
        profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " does not exist"));
    }

    /**
     * Method to get the list of friends for a given profile
     *
     * @param profileId The id of the profile to check
     * @return The list of friends for the given profile
     */
    private List<Long> getFriendsList(Long profileId) {
        return friendshipRepository.getFriendsList(profileId);
    }


}
