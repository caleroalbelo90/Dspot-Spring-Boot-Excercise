package com.demo.profile.main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friendship", indexes = {@Index(name = "idx_profiles_profileId", columnList = "profile_id")})
public class Friendship {

    @Id
    @SequenceGenerator(
            name = "friendship_sequence",
            sequenceName = "friendship_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friendship_sequence")
    private Long id;

    private long profile_id;
    private long friend_id;

    public Friendship(long profile_id, long friend_id) {
        this.profile_id = profile_id;
        this.friend_id = friend_id;
    }

    public Friendship() {
    }

    public Friendship(Long id, long profile_id, long friend_id) {
        this.id = id;
        this.profile_id = profile_id;
        this.friend_id = friend_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(long profile_id) {
        this.profile_id = profile_id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }
}
