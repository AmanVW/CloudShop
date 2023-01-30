package com.cloudshop.profilebs.controller;

//import com.cloudshop.exceptionhandler.exceptions.UserNotFoundException;
import com.cloudshop.profilebs.model.Profile;
import com.cloudshop.profilebs.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/profile-bs/api/v1")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // create
    @PostMapping("/profile")
    public void createProfile(@RequestBody @Valid Profile profile) {
        profileService.createProfile(profile);
    }

    // list all
    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getAllProfiles() {

        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
    }

    // get by id
    @GetMapping("/profiles/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable(name = "id") String id) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//        if(userDetails.getId() != Long.parseLong(id)) {
//            throw new UserNotFoundException("NOT ALLOWED");
//        }
        return new ResponseEntity<>(profileService.getProfileById(Integer.parseInt(id)), HttpStatus.OK);
    }

    // get by username
    @GetMapping("/profiles/{username}")
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable(name = "username") String username) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//        if(!userDetails.getUsername().equalsIgnoreCase(username)) {
//            throw new UserNotFoundException("NOT ALLOWED");
//        }
        return new ResponseEntity<>(profileService.getProfileByUsername(username), HttpStatus.OK);
    }

    // get by email
    @GetMapping("/profiles/{email}")
    public ResponseEntity<Profile> getProfileByEmail(@PathVariable(name = "email") String email) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//        if(!userDetails.getEmail().equalsIgnoreCase(email)) {
//            throw new UserNotFoundException("NOT ALLOWED");
//        }
        return new ResponseEntity<>(profileService.getProfileByEmail(email), HttpStatus.OK);
    }

    // update by id
    @PutMapping("/profiles/{id}")
    public ResponseEntity<Profile> updateProfileById(@PathVariable(name = "id") String id,
                                                     @RequestBody Profile profile) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//        if(userDetails.getId() != Long.parseLong(id)) {
//            throw new UserNotFoundException("NOT ALLOWED");
//        }
        return new ResponseEntity<>(profileService.updateProfileById(Integer.parseInt(id),profile), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/profiles/{id}")
    public void deleteProfileById(@PathVariable(name = "id") String id) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//        if(userDetails.getId() != Long.parseLong(id)) {
//            throw new UserNotFoundException("NOT ALLOWED");
//        }
        profileService.deleteProfileById(Integer.parseInt(id));
    }
}
