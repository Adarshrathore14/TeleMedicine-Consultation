package com.telemedicine.registration.customannotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class ValidateAgeCheckTest {
    private ValidateAgeCheck validateAgeCheck;
    @BeforeEach
    void setUp() {
        validateAgeCheck = new ValidateAgeCheck();
    }
    @Test
    void testValidAge() {
        assertTrue(validateAgeCheck.isValid(LocalDate.of(1990, 1, 1), null));
    }

    @Test
    void testNullValue() {
        assertFalse(validateAgeCheck.isValid(null, null));
    }

    @Test
    void testUnderAge() {
        assertFalse(validateAgeCheck.isValid(LocalDate.now().minusYears(17), null));
    }
}