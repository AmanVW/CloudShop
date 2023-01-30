package com.cloudshop.profilebs.service;

import com.cloudshop.profilebs.model.Profile;

import java.util.List;

public interface ProfileService {
    void createProfile(Profile profile);

    List<Profile> getAllProfiles();

    Profile getProfileById(int id);

    Profile getProfileByUsername(String username);

    Profile getProfileByEmail(String email);

    Profile updateProfileById(int id, Profile profile);

    void deleteProfileById(int id);
}
