package com.dspot.profile.main.repository;

import com.dspot.profile.main.model.profile.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository actualProfileRepository;

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new com.dspot.profile.main.model.profile.Profile(
                "John",
                "Doe",
                "john@example.com",
                "John",
                "Doe",
                "john@example.com",
                "Doe",
                "john@example.com",
                true);
    }

    @Test
    void testAddNewProfile() {
        // Act
        actualProfileRepository.save(profile);
        List<Profile> fullList = actualProfileRepository.findAll();

        // Assert
        assertNotNull(fullList);
        assertEquals(fullList.size(), 1);
    }
}