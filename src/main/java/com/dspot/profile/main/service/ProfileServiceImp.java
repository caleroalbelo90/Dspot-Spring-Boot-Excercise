package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.repository.FriendshipRepository;
import com.dspot.profile.main.repository.ProfileRepository;
import com.dspot.profile.main.util.ObjectUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
     * Check if the profile exists in the database
     *
     * @param profileId The id of the profile to check
     */
    private boolean doesProfileExist(Long profileId) {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        return optionalProfile.isPresent();
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

        if (profileOptional.isPresent()) {
            return new ResponseEntity<>(profileOptional.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id " + id + " does not exist");
        }
    }

    @Override
    public ResponseEntity<?> getFriends(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);

        if (profileOptional.isPresent()) {
            return new ResponseEntity<>(friendshipRepository.getFriendsList(profileId), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id " + profileId + " does not exist");
        }
    }

    @Override
    public ResponseEntity<?> getShortestConnection(Long sourceProfileId, Long targetProfileId) {

        validateSourceAndTargetProfiles(sourceProfileId, targetProfileId);


        // Determine the shortest connection
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

    private void validateSourceAndTargetProfiles(Long sourceProfileId, Long targetProfileId) {
        // Verify both profiles are not the same
        if (Objects.equals(sourceProfileId, targetProfileId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profiles cannot be the same");

        // Verify both profiles exist
        validateExistingProfile(sourceProfileId);
        validateExistingProfile(targetProfileId);
    }

    private void validateExistingProfile(Long profileIdToVerify) {
        if (!doesProfileExist(profileIdToVerify))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id " + profileIdToVerify + " does not exist");
    }

    @Override
    public ResponseEntity<?> registerNewProfile(Profile profile) {
        //For now, we are not checking if the profile already exists
        return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.CREATED);
    }

    @Override
    public void deleteProfile(Long profileId) {
        boolean exists = profileRepository.existsById(profileId);

        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id " + profileId + " does not exist");
        }

        profileRepository.deleteById(profileId);
    }

    @Override
    @Transactional
    public ResponseEntity<Profile> updateProfile(Long profileId, Profile updatedProfile) {
        //First, check if the profile exists
        Optional<Profile> existingProfile = profileRepository.findById(profileId);

        if (existingProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile with id " + profileId + " does not exist");
        }

        Profile profileToUpdate = existingProfile.get();

        if (!Objects.equals(profileToUpdate, updatedProfile)) {
            //Update the profile
            ObjectUpdater.updateFields(profileToUpdate, updatedProfile);
            profileToUpdate = profileRepository.save(profileToUpdate);
        }

        return new ResponseEntity<>(profileToUpdate, HttpStatus.OK);
    }

}