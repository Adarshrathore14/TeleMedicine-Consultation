package com.telemedicine.authentication.configurations;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserInfoToUserServiceTest {
    private UserInfoToUserService userInfoToUserService;
    private AuthenticationEntity authenticationEntity;
    @BeforeEach
    void setUp() {
        authenticationEntity = new AuthenticationEntity("123","userName","user@gmail.com",
                "user","patient");
        userInfoToUserService = new UserInfoToUserService(authenticationEntity);
    }

    @Test
    void getAuthorities() {
        assertNotNull(userInfoToUserService.getAuthorities());
        assertEquals(1,userInfoToUserService.getAuthorities().size());
    }

    @Test
    void getPassword() {
        assertEquals(authenticationEntity.getPassword(),userInfoToUserService.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals(authenticationEntity.getEmail(),userInfoToUserService.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userInfoToUserService.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userInfoToUserService.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userInfoToUserService.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userInfoToUserService.isEnabled());
    }
}