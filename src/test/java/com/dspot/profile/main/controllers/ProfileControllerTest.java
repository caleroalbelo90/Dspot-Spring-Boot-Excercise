package com.dspot.profile.main.controllers;

import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.service.ProfileService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@WebMvcTest
public class ProfileControllerTest {

    @MockBean
    ProfileService profileService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProfiles() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/profile?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).getProfilesPage(0, 50);
    }

    @Test
    void getProfileById() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/profile/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).getProfile(10L);

    }

    @Test
    void registerNewProfile() throws Exception {
        // Arrange
        Profile profile = new Profile(
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
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/profile")
                        .content(gson.toJson(profile))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).registerNewProfile(profile);

    }

    @Test
    void deleteProfile() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/profile/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).deleteProfile(10L);
    }

    @Test
    void getShortestConnection() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/profile/10/shortest-connection/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).getShortestConnection(10L, 20L);
    }

    @Test
    void getFriends() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/profile/10/friends")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1)).getFriends(10L);
    }

    @Test
    void updateProfile() throws Exception {
        // Arrange
        Profile profile = new Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doej",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/profile/18")
                        .content(gson.toJson(profile))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        verify(profileService, times(1))
                .updateProfile(18L,
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