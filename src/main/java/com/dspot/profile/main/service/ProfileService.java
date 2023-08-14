package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.model.profile.ProfileDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfileService {

    /**
     * Get the full list profile
     *
     * @param page page number
     * @param size size number of elements per page
     */
    Page<Profile> getProfilesPage(int page, int size);

    /**
     * Get a profile by id
     *
     * @param id id of the profile to get
     */
    Profile getProfile(Long id);

    /**
     * Get the full list of friends for a given profile
     *
     * @param profileId id of the profile to get the friends list
     */
    List<Long> getFriends(Long profileId);


    /**
     * Get the shortest connection between two profiles
     *
     * @param sourceProfileId Starting profile
     * @param targetProfileId Profile to connect to
     */
    List<Long> getShortestConnection(Long sourceProfileId, Long targetProfileId);

    /**
     * Register a new profile
     *
     * @param profile profile to register
     */
    Profile registerNewProfile(Profile profile);

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
    Profile updateProfile(Long profileId, Profile profile);
}