package com.dspot.profile.main.service;

import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.repository.FriendshipRepository;
import com.dspot.profile.main.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepositoryMock;

    @Mock
    FriendshipRepository friendshipRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProfiles() {
        // Arrange
        List<Profile> profiles = Arrays.asList(
                new Profile(
                        "John",
                        "Doe",
                        "john@example.com",
                        "John",
                        "Doe",
                        "john@example.com",
                        "Doe",
                        "john@example.com",
                        true),
                new Profile(
                        "John",
                        "Doe",
                        "john@example.com",
                        "John",
                        "Doe",
                        "john@example.com",
                        "Doe",
                        "john@example.com",
                        true)
        );
        Page<Profile> page = new PageImpl<>(profiles);
        Mockito.when(profileRepositoryMock.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<Page<Profile>> response = profileService.getProfilesPage(0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    void getProfile() {
        // Arrange
        Profile mockProfile = new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);

        Mockito.when(profileRepositoryMock.findById(1L)).thenReturn(Optional.of(mockProfile));
        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<?> response = profileService.getProfile(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getFriends() {
        // Arrange
        List<Long> friendsList = Arrays.asList(2L, 3L);
        Mockito.when(profileRepositoryMock.findById(1L)).thenReturn(Optional.of(new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true)));

        Mockito.when(friendshipRepositoryMock.getFriendsList(1L)).thenReturn(friendsList);

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, friendshipRepositoryMock);

        // Act
        ResponseEntity<?> response = profileService.getFriends(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(friendsList, response.getBody());
    }

    @Test
    void getShortestConnection() {
        // Arrange
        List<Long> friendsList1 = Arrays.asList(2L, 3L);
        List<Long> friendsList2 = Arrays.asList(1L);

        Mockito.when(profileRepositoryMock.findById(1L)).thenReturn(Optional.of(new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true)));

        Mockito.when(profileRepositoryMock.findById(2L)).thenReturn(Optional.of(new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true)));

        Mockito.when(profileRepositoryMock.findById(3L)).thenReturn(Optional.of(new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true)));


        Mockito.when(friendshipRepositoryMock.getFriendsList(1L)).thenReturn(friendsList1);
        Mockito.when(friendshipRepositoryMock.getFriendsList(2L)).thenReturn(friendsList2);
        Mockito.when(friendshipRepositoryMock.getFriendsList(3L)).thenReturn(friendsList2);

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, friendshipRepositoryMock);

        // Act
        ResponseEntity<?> response = profileService.getShortestConnection(1L, 2L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Long> shortestConnection = (List<Long>) response.getBody();
        assertEquals(Arrays.asList(1L, 2L), shortestConnection);
    }

    @Test
    void registerNewProfile() {
        //Arrange
        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        Profile newProfile = new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);

        // Act
        ResponseEntity<?> response = profileService.registerNewProfile(newProfile);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("The request was processed successfully.", response.getBody());
        Mockito.verify(profileRepositoryMock, Mockito.times(1)).save(newProfile);
    }

    @Test
    void deleteProfile() {
        // Arrange
        Long existingProfileId = 1L;
        Mockito.when(profileRepositoryMock.existsById(existingProfileId)).thenReturn(true);

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<String> response = profileService.deleteProfile(existingProfileId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The request was processed successfully.", response.getBody());
    }

    @Test
    public void testDeleteProfile_NotFound() {
        // Arrange
        Long nonExistingProfileId = 100L;
        Mockito.when(profileRepositoryMock.existsById(nonExistingProfileId)).thenReturn(false);

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<String> response = profileService.deleteProfile(nonExistingProfileId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Profile with id " + nonExistingProfileId + " does not exist", response.getBody());
    }

    @Test
    void updateProfile() {
        // Arrange
        Long existingProfileId = 1L;
        Profile existingProfile = new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);
        Mockito.when(profileRepositoryMock.findById(existingProfileId)).thenReturn(Optional.of(existingProfile));

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<?> response = profileService.updateProfile(existingProfileId, new Profile("Jane", "Smith"));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Profile updatedProfile = (Profile) response.getBody();
        assertEquals("Jane", updatedProfile.getFirst_name());
        assertEquals("Smith", updatedProfile.getLast_name());
    }

    @Test
    public void testUpdateProfile_NoChanges() {
        // Arrange
        Long existingProfileId = 1L;
        Profile existingProfile = new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);
        Mockito.when(profileRepositoryMock.findById(existingProfileId)).thenReturn(Optional.of(existingProfile));

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<?> response = profileService.updateProfile(existingProfileId, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nothing to update", response.getBody());
    }

    @Test
    public void testUpdateProfile_ProfileNotFound() {
        // Arrange
        Long nonExistingProfileId = 100L;
        Mockito.when(profileRepositoryMock.findById(nonExistingProfileId)).thenReturn(Optional.empty());

        ProfileService profileService = new ProfileServiceImp(profileRepositoryMock, null);

        // Act
        ResponseEntity<?> response = profileService.updateProfile(nonExistingProfileId, new Profile("Jane", "Smith"));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Profile with id " + nonExistingProfileId + " does not exist", response.getBody());
    }

}