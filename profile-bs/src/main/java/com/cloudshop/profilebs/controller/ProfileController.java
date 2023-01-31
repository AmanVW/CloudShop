package com.cloudshop.profilebs.controller;

import com.cloudshop.exceptionhandler.exceptions.UnauthorizedAccessException;
import com.cloudshop.profilebs.model.Profile;
import com.cloudshop.profilebs.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cloudshop.profilebs.constants.ProfileBsConstants.*;

@RestController
@RequestMapping(PROFILE_BASEPATH)
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // create
    @PostMapping(PROFILE_PATH)
    public void createProfile(@RequestBody @Valid Profile profile) {

        profileService.createProfile(profile);
    }

    // list all
    @GetMapping(PROFILES_PATH)
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
    }

    // get by id
    @GetMapping(PROFILES_PATH + ID_PATH)
    public ResponseEntity<Profile> getProfileById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(profileService.getProfileById(Integer.parseInt(id)), HttpStatus.OK);
    }

    // get by username
    @GetMapping(PROFILES_PATH + USERNAME_PATH)
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(profileService.getProfileByUsername(username), HttpStatus.OK);
    }

    // get by email
    @GetMapping(PROFILES_PATH + EMAIL_PATH)
    public ResponseEntity<Profile> getProfileByEmail(@PathVariable(name = "email") String email) {
        return new ResponseEntity<>(profileService.getProfileByEmail(email), HttpStatus.OK);
    }

    // update by id
    @PutMapping(PROFILES_PATH + ID_PATH)
    public ResponseEntity<Profile> updateProfileById(@PathVariable(name = "id") String id,
                                                     @RequestBody @Valid Profile profile) {
        return new ResponseEntity<>(profileService.updateProfileById(Integer.parseInt(id),profile), HttpStatus.OK);
    }

    // delete
    @DeleteMapping(PROFILES_PATH + ID_PATH)
    public void deleteProfileById(@PathVariable(name = "id") String id) {
        profileService.deleteProfileById(Integer.parseInt(id));
    }
}
