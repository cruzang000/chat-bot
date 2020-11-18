package edu.matc.entjava.socialite.service;

import edu.matc.entjava.socialite.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The type Field validator.
 */
public class FieldValidator {

    private final Logger logger = LogManager.getLogger(this.getClass());
    GenericDao genericDao;

    /**
     * Validate only letters string with optional word length with tests the length of the string.
     *
     * @param string     the string
     * @param wordLength the word length
     * @return the boolean
     */
    public String validateOnlyLettersString(String string, int wordLength, String errorMessage) {
        boolean isValid = string.matches("[A-za-z]+");

        if (wordLength > 0 && isValid) isValid = string.length() == wordLength;

        return isValid ? "" : errorMessage;
    }

    /**
     * validates date of birth in format "YYYY-MM-DD" using DatetimeFormatter and LocalDate classes
     * @param dateOfBirth date of birth
     * @return
     */
    public String validateDate(String dateOfBirth, String errorMessage) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String message = "";

        try {
            LocalDate.parse(dateOfBirth, dateFormatter);
        } catch (DateTimeParseException e) {
            message = errorMessage;
        }

        return message;
    }

    /**
     * takes local date format date of birth and checks if over passed in date in param
     * @param dateOfBirth date of birth
     * @return
     */
    public String validateMinAge(String dateOfBirth, int minAge, String errorMessage) {
        int age = 0;

        try {
            age = Period.between(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            logger.error("invalid date", e);
        }

        return age >= minAge ? "" : errorMessage;
    }

    /**
     * validate any string by regex
     * @param string string to validate
     * @param regex regex for validation
     * @return
     */
    public String validateByRegex(String string, String regex, String errorMessage) {
        return string.matches(regex) ? "" : errorMessage;
    }

    /**
     * validates email against regex, returns error message from param
     * @param email email
     * @param errorMessage error message to return on error
     * @return
     */
    public String validateEmail(String email,  String errorMessage) {
        return email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$") ? "" : errorMessage;
    }

    /**
     * validates if property passed in has duplicate value passed in, returns param error messagew or empty
     * @param type type of class for generic dai
     * @param fieldName field to validate against
     * @param value field to validate for
     * @param errorMessage error message to return on error
     * @return error message or empty string
     */
    public String validateDuplicateProperty(Class<?> type, String fieldName, String value, String errorMessage) {
        logger.info("count: " + new GenericDao(type).getByPropertyValue(fieldName, value).size());
        return new GenericDao(type).getByPropertyValue(fieldName, value).size() == 0 ? "" : errorMessage;
    }
}
