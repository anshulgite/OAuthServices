package com.auth.OAuthService.user;

import java.util.List;
import java.util.Optional;

public interface UserInterface {

    public UserEntity saveUser(UserEntity user);

    public UserEntity getUser(String username);

    public UserEntity updateUser(UserEntity user);

    public boolean deleteUser(Long userId);

    public UserEntity getUserById(Long id);

    public List<UserEntity> getAllUsers();

    Optional<UserEntity> findByEmail(String email);
}
