package com.dspot.profile.main.service;

import com.dspot.profile.main.errors.InvalidInputException;
import com.dspot.profile.main.errors.ProfileNotFoundException;
import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.repository.FriendshipRepository;
import com.dspot.profile.main.repository.ProfileRepository;
import com.dspot.profile.main.util.ObjectUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Profile> getProfilesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return profileRepository.findAll(pageable);
    }

    @Override
    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));
    }

    @Override
    public List<Long> getFriends(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);

        if (profileOptional.isPresent()) {
            return friendshipRepository.getFriendsList(profileId);
        }

        throw new ProfileNotFoundException(profileId);
    }

    @Override
    public List<Long> getShortestConnection(Long sourceProfileId, Long targetProfileId) {
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

            List<Long> friends = friendshipRepository.getFriendsList(currentProfileId);

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

        return shortestConnection;
    }

    @Override
    public Profile registerNewProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(Long profileId) {
        profileRepository.findById(profileId).ifPresentOrElse(
                profile -> profileRepository.deleteById(profileId),
                () -> { throw new ProfileNotFoundException(profileId); }
        );
    }

    @Override
    @Transactional
    public Profile updateProfile(Long profileId, Profile updatedProfile) {
        Profile profileToUpdate = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        // Update the profile
        ObjectUpdater.updateFields(profileToUpdate, updatedProfile);

        return profileRepository.save(profileToUpdate);
    }

    private void validateSourceAndTargetProfiles(Long sourceProfileId, Long targetProfileId) {
        if (Objects.equals(sourceProfileId, targetProfileId)) {
            throw new InvalidInputException("Profiles cannot be the same");
        }

        profileRepository.findById(sourceProfileId)
                .orElseThrow(() -> new ProfileNotFoundException(sourceProfileId));

        profileRepository.findById(targetProfileId)
                .orElseThrow(() -> new ProfileNotFoundException(targetProfileId));
    }

}
