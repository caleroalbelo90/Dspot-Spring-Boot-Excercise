package com.demo.profile.main.controller;


import com.demo.profile.main.model.Profile;
import com.demo.profile.main.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping(path="{profileId}")
    public Profile getProfileById(@PathVariable("profileId") Long profileId) {
        return profileService.getProfile(profileId);
    }

    @GetMapping("/{profileId1}/shortest-connection/{profileId2}")
    public List<Long> getShortestConnection(@PathVariable Long profileId1, @PathVariable Long profileId2) {
        return profileService.getShortestConnection(profileId1, profileId2);
    }

}
