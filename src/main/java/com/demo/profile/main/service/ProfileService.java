package com.demo.profile.main.service;

import com.demo.profile.main.model.Profile;
import com.demo.profile.main.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
