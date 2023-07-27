package com.dspot.profile.main.model;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", profile_id=" + profile_id +
                ", friend_id=" + friend_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return profile_id == that.profile_id && friend_id == that.friend_id && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile_id, friend_id);
    }
}
