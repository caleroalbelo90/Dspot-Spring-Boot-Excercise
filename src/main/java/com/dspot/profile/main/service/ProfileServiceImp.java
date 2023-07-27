package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.repository.FriendshipRepository;
import com.dspot.profile.main.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public ProfileServiceImp(ProfileRepository profileRepository, FriendshipRepository friendshipRepository) {
        this.profileRepository = profileRepository;
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * Check if the profile exists, or throw an exception if not found
     *
     * @param profileId The id of the profile to check
     */
    private ResponseEntity<?> checkIfProfileExists(Long profileId) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);

        return optionalProfile.<ResponseEntity<?>>map(
                        profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Profile with id " + profileId + " does not exist")
                );
    }

    /**
     * Method to get the list of friends for a given profile
     *
     * @param profileId The id of the profile to check
     * @return The list of friends for the given profile
     */
    private ResponseEntity<List<Long>> getFriendsList(Long profileId) {
        return new ResponseEntity<>(friendshipRepository.getFriendsList(profileId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Profile>> getProfilesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return new ResponseEntity<>(profileRepository.findAll(pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getProfile(Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);

        return profileOptional.
                <ResponseEntity<?>>map(
                profile ->
                        new ResponseEntity<>(profile, HttpStatus.OK))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Profile with id " + id + " does not exist"));
    }

    @Override
    public ResponseEntity<?> getFriends(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);

        return profileOptional.
                <ResponseEntity<?>>map(
                profile ->
                        new ResponseEntity<>(friendshipRepository.getFriendsList(profileId), HttpStatus.OK))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Profile with id " + profileId + " does not exist"));
    }

    @Override
    public ResponseEntity<?> getShortestConnection(Long sourceProfileId, Long targetProfileId) {

        // Verify both profiles are not the same
        if (Objects.equals(sourceProfileId, targetProfileId)) {
            return new ResponseEntity<>("Profiles cannot be the same", HttpStatus.BAD_REQUEST);
        }

        // Verify both profiles exist
        if (checkIfProfileExists(sourceProfileId).getStatusCode() != HttpStatus.OK)
            return checkIfProfileExists(sourceProfileId);

        if (checkIfProfileExists(targetProfileId).getStatusCode() != HttpStatus.OK)
            return checkIfProfileExists(targetProfileId);

        List<Long> shortestConnection = new ArrayList<>();

        Set<Long> visitedProfiles = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();
        Map<Long, Long> parentMap = new HashMap<>();

        visitedProfiles.add(sourceProfileId);
        queue.add(sourceProfileId);
        parentMap.put(sourceProfileId, null);

        while (!queue.isEmpty()) {
            Long currentProfileId = queue.poll();

            if (currentProfileId.equals(targetProfileId)) {
                break; // We found the shortest connection
            }

            List<Long> friends = getFriendsList(currentProfileId).getBody();

            if (friends == null || friends.isEmpty()) {
                continue;
            }

            for (Long friendId : friends) {
                if (!visitedProfiles.contains(friendId)) {
                    visitedProfiles.add(friendId);
                    queue.add(friendId);
                    parentMap.put(friendId, currentProfileId);
                }
            }
        }

        if (parentMap.containsKey(targetProfileId)) {
            Long currentId = targetProfileId;
            while (currentId != null) {
                shortestConnection.add(0, currentId);
                currentId = parentMap.get(currentId);
            }
        }

        return new ResponseEntity<>(shortestConnection, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> registerNewProfile(Profile profile) {
        //For now, we are not checking if the profile already exists
        profileRepository.save(profile);
        return ResponseEntity.ok("The request was processed successfully.");
    }

    @Override
    public ResponseEntity<String> deleteProfile(Long profileId) {
        boolean exists = profileRepository.existsById(profileId);

        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile with id " + profileId + " does not exist");
        }

        profileRepository.deleteById(profileId);
        return ResponseEntity.ok("The request was processed successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProfile(Long profileId,
                                           String img,
                                           String firstName,
                                           String lastName,
                                           String phone,
                                           String address,
                                           String city,
                                           String state,
                                           String zipcode) {

        //First, check if the profile exists
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);

        if (optionalProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile with id " + profileId + " does not exist");
        }

        boolean someFieldChanged = false;
        Profile profile = optionalProfile.get();

        //Then, update the values only if the new values are not null and different from the current ones
        if (img != null && img.length() > 0 && !img.equals(profile.getImg())) {
            profile.setImg(img);
            someFieldChanged = true;
        }
        if (firstName != null && firstName.length() > 0 && !firstName.equals(profile.getFirst_name())) {
            profile.setFirst_name(firstName);
            someFieldChanged = true;
        }
        if (lastName != null && lastName.length() > 0 && !lastName.equals(profile.getLast_name())) {
            profile.setLast_name(lastName);
            someFieldChanged = true;
        }
        if (phone != null && phone.length() > 0 && !phone.equals(profile.getPhone())) {
            profile.setPhone(phone);
            someFieldChanged = true;
        }
        if (address != null && address.length() > 0 && !address.equals(profile.getAddress())) {
            profile.setAddress(address);
            someFieldChanged = true;
        }
        if (city != null && city.length() > 0 && !city.equals(profile.getCity())) {
            profile.setCity(city);
            someFieldChanged = true;
        }
        if (state != null && state.length() > 0 && !state.equals(profile.getState())) {
            profile.setState(state);
            someFieldChanged = true;
        }
        if (zipcode != null && zipcode.length() > 0 && !zipcode.equals(profile.getZipcode())) {
            profile.setZipcode(zipcode);
            someFieldChanged = true;
        }

        if (someFieldChanged) {
            profileRepository.save(profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing to update");

    }
}