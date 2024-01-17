package com.ztcollins.JavaEducator.UnitTests;

import com.ztcollins.JavaEducator.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testId() {
        Long expectedId = 1L;
        user.setId(expectedId);
        assertEquals(expectedId, user.getId());
    }

    @Test
    void testEmail() {
        String expectedEmail = "test@example.com";
        user.setEmail(expectedEmail);
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    void testName() {
        String expectedName = "John Doe";
        user.setName(expectedName);
        assertEquals(expectedName, user.getName());
    }

    @Test
    void testAdminStatus() {
        user.setAdminStatus(true);
        assertTrue(user.getAdminStatus());

        user.setAdminStatus(false);
        assertFalse(user.getAdminStatus());
    }

    @Test
    void testScore() {
        int expectedScore = 100;
        user.setScore(expectedScore);
        assertEquals(expectedScore, user.getScore());
    }

    @Test
    void testOperatingMode() {
        String expectedMode = "Active";
        user.setOperatingMode(expectedMode);
        assertEquals(expectedMode, user.getOperatingMode());
    }

    @Test
    void testToString() {
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("John Doe");
        user.setScore(100);
        user.setOperatingMode("Active");
        
        String expectedString = "User{id=1, email=test@example.com, name='John Doe', score=100, mode=Active}";
        assertEquals(expectedString, user.toString());
    }
}