package com.thomaskioko.jkuat_codelab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains helper methods to validate strings.
 *
 * @author kioko
 */

public class StringUtils {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    /**
     * Helper method to validate E-mail address with regular expression
     *
     * @param emailAddress {@link String} E-mail Address
     * @return {@link Boolean} True/False
     */
    public static boolean validateEmailAddress(String emailAddress) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddress);
        return matcher.find();
    }

    /**
     * Helper method to validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean validatePassword(final String password) {

        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();

    }
}
