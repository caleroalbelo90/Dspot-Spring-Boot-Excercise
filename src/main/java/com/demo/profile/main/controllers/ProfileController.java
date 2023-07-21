package com.demo.profile.main.controllers;


import com.demo.profile.main.model.Profile;
import com.demo.profile.main.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profile")
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

    @GetMapping(path = "{profileId}")
    public Profile getProfileById(@PathVariable("profileId") Long profileId) {
        return profileService.getProfile(profileId);
    }

    @PostMapping
    public void registerNewProfile(@RequestBody Profile profile) {
        profileService.registerNewProfile(profile);
    }

    @DeleteMapping(path = "{profileId}")
    public void deleteStudent(@PathVariable("profileId") Long profileId) {
        profileService.deleteProfile(profileId);
    }

    @GetMapping("/{profileId1}/shortest-connection/{profileId2}")
    public List<Long> getShortestConnection(@PathVariable Long profileId1, @PathVariable Long profileId2) {
        return profileService.getShortestConnection(profileId1, profileId2);
    }

    @GetMapping("/{profileId}/friends")
    public List<Long> getFriends(@PathVariable Long profileId) {
        return profileService.getFriends(profileId);
    }


    @PutMapping(path = "{profileId}")
    public Profile updateProfile(
            @PathVariable("profileId") Long profileId,
            @RequestBody Profile profile
    ) {
        if (profile == null) {
            throw new IllegalStateException("Profile cannot be null");
        }
        return profileService.updateStudent(profileId,
                profile.getImg(),
                profile.getFirst_name(),
                profile.getLast_name(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getCity(),
                profile.getState(),
                profile.getZipcode());
    }


}
