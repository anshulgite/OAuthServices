package com.auth.OAuthService.user;

import com.auth.OAuthService.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserInterface userService;

    public UserController(UserInterface userService) {
        this.userService = userService;
    }


    @GetMapping("/{username}")
    public ApiResponse<UserEntity> getUser(@PathVariable String username) {
        try {
            UserEntity user = userService.getUser(username);
            if (user != null) {
                return ApiResponse.success(user, "User found successfully");
            } else {
                return ApiResponse.error("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ApiResponse<UserEntity> getUserById(@PathVariable Long id) {
        try {
            UserEntity user = userService.getUserById(id);
            if (user != null) {
                return ApiResponse.success(user, "User found successfully");
            } else {
                return ApiResponse.error("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<java.util.List<UserEntity>> getAllUsers() {
        try {
            java.util.List<UserEntity> users = userService.getAllUsers();
            return ApiResponse.success(users, "Users retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<UserEntity> updateUser(@RequestBody UserEntity user) {
        try {
            UserEntity updatedUser = userService.updateUser(user);
            return ApiResponse.success(updatedUser, "User updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ApiResponse.success("User deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-mobile")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateMobile(@RequestParam String mobile, Principal principal) {
        // Principal se current logged-in user ka email milega
        String email = principal.getName();
        UserEntity user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        log.info("User found : {}", user);
        user.setPhone(mobile);
        userService.updateUser(user);

        return ApiResponse.success("Mobile number updated successfully!");
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("hasRole('ADMIN')")
//    @PostMapping("/test-login-and-user")
//    public ApiResponse<String> testLoginAndUer(@RequestParam String test, Authentication authentication) {
//        // get current logged-in user details from security context
//
//        String username = authentication.getName();
//        CustomeUserDetails customUserDetails = (CustomeUserDetails) authentication.getPrincipal();
//        Long userId = customUserDetails.getUserId();
//        String email = customUserDetails.getEmail();
//        String userRole = customUserDetails.getAuthorities().toString();
//        String userPassword = customUserDetails.getPassword();
//
//        String combineString = "userId : "+userId+",username  :"+username +" "+test+" email : "+email+" userRole : "+userRole;
//        return ApiResponse.success(combineString, "User logged in successfully");
//    }
}
