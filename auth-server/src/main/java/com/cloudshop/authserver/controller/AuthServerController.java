package com.cloudshop.authserver.controller;

import com.cloudshop.authserver.jwt.JwtUtils;
import com.cloudshop.authserver.model.ERoles;
import com.cloudshop.authserver.model.Role;
import com.cloudshop.authserver.model.User;
import com.cloudshop.authserver.model.payload.request.LoginRequest;
import com.cloudshop.authserver.model.payload.request.SignupRequest;
import com.cloudshop.authserver.model.payload.response.JwtResponse;
import com.cloudshop.authserver.repository.RoleRepository;
import com.cloudshop.authserver.repository.UserRepository;
import com.cloudshop.authserver.service.UserDetailsImpl;
import com.cloudshop.exceptionhandler.exceptions.UsernameAlreadyExistsException;
import com.cloudshop.exceptionhandler.exceptions.RoleNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth-server/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthServerController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signUpRequest) throws RoleNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(String.format("User with username %s already exists.", signUpRequest.getUsername()));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UsernameAlreadyExistsException(String.format("User with email %s already exists.", signUpRequest.getEmail()));
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {

            Optional<Role> optional = roleRepository.findByName(ERoles.ROLE_USER);
            if(optional.isPresent()) {
                roles.add(optional.get());
            } else {
                throw new RoleNotFoundException(String.format("Role %s does not exist", ERoles.ROLE_USER));
            }
        } else {

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERoles.ROLE_ADMIN).orElseThrow(() -> new RoleNotFoundException(String.format("Role %s does not exist", ERoles.ROLE_ADMIN)));
                        roles.add(adminRole);

                        break;

                    case "mod":
                        Role modRole = roleRepository.findByName(ERoles.ROLE_MODERATOR).orElseThrow(() -> new RoleNotFoundException(String.format("Role %s does not exist", ERoles.ROLE_MODERATOR)));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERoles.ROLE_USER).orElseThrow(() -> new RoleNotFoundException(String.format("Role %s does not exist", ERoles.ROLE_USER)));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return new ResponseEntity<>
                (new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
