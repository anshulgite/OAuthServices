package com.auth.OAuthService.user;

import com.auth.OAuthService.common.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class UserService implements UserInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        try {

            Validators.isValidEmail(user.getEmail());
            Validators.isValidPhone(user.getPhone());
            int msgResult = userRepository.checkUserName(user.getUsername());
            if (msgResult > 0) {
                throw new RuntimeException("Username already exists");
            }

            user.setPassword(Validators.encodePassword(user.getPassword()));

            UserEntity save = userRepository.save(user);

            return save;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("Error while saving user", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity user) {
        if (user.getUserId() == null || user.getUserId() <= 0)
            throw new RuntimeException("Invalid user id");
        return saveUser(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (id == null || id <= 0)
            throw new RuntimeException("Invalid user id");
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("user not exist");
        }
    }

//    @Override
//    @Transactional
//    public boolean changePassword(String username, String currentPassword, String newPassword) {
//        try {
//            UserEntity user = userRepository.findByUsername(username);
//            if (user == null) {
//                throw new RuntimeException("User not found");
//            }
//
//            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
//                throw new RuntimeException("Current password is incorrect");
//            }
//
//            user.setPassword(Validators.encodePassword(newPassword));
//            userRepository.save(user);
//
//            return true;
//        } catch (Exception e) {
//            logger.error("Error while changing password", e);
//            throw new RuntimeException(e.getMessage());
//        }
//    }

//    @Override
//    @Transactional
//    public String forgotPassword(String username) {
//        try {
//            if (username == null || username.trim().isEmpty()) {
//                throw new RuntimeException("Username cannot be empty");
//            }
//
//            UserEntity user = userRepository.findByUsername(username);
//            if (user == null) {
//                throw new RuntimeException("User with this username does not exist");
//            }
//
//            String email = user.getEmail();
//            Validators.isValidEmail(email);
//
//            String otp = otpService.generateOtp(email);
//            logger.info("OTP sent to email: {} for username: {}", email, username);
//
//            return "OTP sent to your registered email. Please check your inbox.";
//        } catch (Exception e) {
//            logger.error("Error in forgot password for username: {}", username, e);
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional
//    public String resetPasswordWithOtp(String username, String otp, String newPassword) {
//        try {
//            if (username == null || username.trim().isEmpty()) {
//                throw new RuntimeException("Username cannot be empty");
//            }
//
//            UserEntity user = userRepository.findByUsername(username);
//            if (user == null) {
//                throw new RuntimeException("User with this username does not exist");
//            }
//
//            String email = user.getEmail();
//            Validators.isValidEmail(email);
//
//            if (!otpService.verifyOtp(email, otp)) {
//                throw new RuntimeException("Invalid or expired OTP");
//            }
//
//            Validators.isValidPassword(newPassword);
//            user.setPassword(Validators.encodePassword(newPassword));
//            userRepository.save(user);
//
//            otpService.markOtpAsUsed(email);
//
//            logger.info("Password reset successful for username: {}", username);
//            return "Password reset successful";
//        } catch (Exception e) {
//            logger.error("Error in reset password for username: {}", username, e);
//            throw new RuntimeException(e.getMessage());
//        }
//    }

}
