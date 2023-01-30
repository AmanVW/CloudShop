package com.cloudshop.profilebs.repository;

import com.cloudshop.profilebs.model.Profile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public boolean existsByEmail(String email);
    public boolean existsById(int id);
    public Optional<Profile> findById(int id);
    public Optional<Profile> findByUsername(String username);
    public Optional<Profile> findByEmail(String email);
}
