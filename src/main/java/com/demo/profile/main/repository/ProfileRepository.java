package com.demo.profile.main.repository;

import com.demo.profile.main.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /*@Query("SELECT p FROM profile p WHERE p.id = ?1")
    Optional<Profile> findProfileById(Long id);*/

}
