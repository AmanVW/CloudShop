package com.cloudshop.profilebs.service;

import com.cloudshop.exceptionhandler.exceptions.EmailAlreadyExistsException;
import com.cloudshop.exceptionhandler.exceptions.UserNotFoundException;
import com.cloudshop.profilebs.model.Profile;
import com.cloudshop.profilebs.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void createProfile(Profile profile) {
        if (!profileRepository.existsByEmail(profile.getEmail())) {
            profileRepository.save(profile);
        } else {
            throw new EmailAlreadyExistsException("Email Already exists");
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileById(int id) {
        Optional<Profile> optional = profileRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public Profile getProfileByUsername(String username) {
        Optional<Profile> optional = profileRepository.findByUsername(username);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public Profile getProfileByEmail(String email) {
        Optional<Profile> optional = profileRepository.findByEmail(email);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public Profile updateProfileById(int id, Profile profile) {
        if(profileRepository.existsById(id)) {
            Profile newProfile = Profile.builder()
                    .id(id)
                    .firstName(profile.getFirstName())
                    .middleName(profile.getMiddleName())
                    .lastName(profile.getLastName())
                    .email(profile.getEmail())
                    .phone(profile.getPhone())
                    .dateOfBirth(profile.getDateOfBirth())
                    .gender(profile.getGender())
                    .addresses(profile.getAddresses())
                    .build();

            profileRepository.save(newProfile);
            return newProfile;
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public void deleteProfileById(int id) {
        if(profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }
}
