package com.demo.profile.main.repository;

import com.demo.profile.main.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f.friend_id FROM Friendship f WHERE f.profile_id = :profileId")
    List<Long> getFriendsList(@Param("profileId") Long profileId);

}
