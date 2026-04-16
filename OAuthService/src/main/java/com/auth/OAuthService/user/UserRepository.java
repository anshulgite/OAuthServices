package com.auth.OAuthService.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //check username is already exists return boolean true or false if exists use native query
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM users WHERE username = :username", nativeQuery = true)
    int checkUserName(String username);

    UserEntity findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
