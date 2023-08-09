package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProfileService {

    /**
     * Get the full list profile
     *
     * @param page page number
     * @param size size number of elements per page
     */
    ResponseEntity<Page<Profile>> getProfilesPage(int page, int size);

    /**
     * Get a profile by id
     *
     * @param id id of the profile to get
     */
    ResponseEntity<?> getProfile(Long id);

    /**
     * Get the full list of friends for a given profile
     *
     * @param profileId id of the profile to get the friends list
     */
    ResponseEntity<?> getFriends(Long profileId);


    /**
     * Get the shortest connection between two profiles
     *
     * @param sourceProfileId Starting profile
     * @param targetProfileId Profile to connect to
     */
    ResponseEntity<?> getShortestConnection(Long sourceProfileId, Long targetProfileId);

    /**
     * Register a new profile
     *
     * @param profile profile to register
     */
    ResponseEntity<?> registerNewProfile(Profile profile);

    /**
     * Delete a profile
     *
     * @param profileId id of the profile to delete
     */
    void deleteProfile(Long profileId);

    /**
     * Update a profile
     *
     * @param profileId id of the profile to update
     * @param profile       profile to update
     * @return the updated profile
     */
    ResponseEntity<Profile> updateProfile(Long profileId, Profile profile);
}