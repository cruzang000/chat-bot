package edu.matc.entjava.socialite.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Field validator test.
 */
class FieldValidatorTest {

    /**
     * The Field validator.
     */
    FieldValidator fieldValidator;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        fieldValidator = new FieldValidator();
    }

    /**
     * Validate only letters string.
     */
    @Test
    void validateOnlyWordString() {
        // string is only letters
        assertEquals("", fieldValidator.validateOnlyLettersString("dasfsa" , 0, "error"));
        // string is only letters and 3 characters long
        assertEquals("", fieldValidator.validateOnlyLettersString("ddaa" , 4, "error"));
        // string is not valid contains numbers
        assertEquals("error", fieldValidator.validateOnlyLettersString("das999a" , 0, "error"));
    }

    /**
     * validate date
     */
    @Test
    void validateDate() {
        // string is only letters
        assertEquals("", fieldValidator.validateDate("1994-12-09", "error with date"));
    }
}