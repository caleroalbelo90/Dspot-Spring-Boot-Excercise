package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
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
    ResponseEntity<String> registerNewProfile(Profile profile);

    /**
     * Delete a profile
     *
     * @param profileId id of the profile to delete
     */
    ResponseEntity<String> deleteProfile(Long profileId);

    /**
     * Update a profile
     *
     * @param profileId id of the profile to update
     * @param img profile image
     * @param firstName profile first name
     * @param lastName profile last name
     * @param phone profile phone
     * @param address profile address
     * @param city profile city
     * @param state profile state
     * @param zipcode profile zipcode
     */
    ResponseEntity<?> updateProfile(Long profileId,
                                    String img,
                                    String firstName,
                                    String lastName,
                                    String phone,
                                    String address,
                                    String city,
                                    String state,
                                    String zipcode);
}