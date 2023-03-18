package heedoitdox.deliverysystem.domain;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final int MIN_SIZE = 12;
    private static final int MAX_SIZE = 25;
    private static final String REGEX_UPPER_CASE = ".*[A-Z].*";
    private static final String REGEX_LOWER_CASE = ".*[a-z].*";
    private static final String REGEX_NUMBER = ".*[0-9].*";
    private static final String REGEX_SPECIAL_CHARACTERS = ".*[\\W_]+.*";
    private static final String REGEX_EXCLUDE_CHARACTERS = "^[a-zA-Z0-9!@#$%^&*()+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]*$";
    private static final String REGEX_SIZE= "^.{" + MIN_SIZE + "," + MAX_SIZE + "}$";

    public static boolean validate(String target) {
        int validConditionCount = 0;

        if(isMatch(target, REGEX_SIZE) && isMatch(target, REGEX_EXCLUDE_CHARACTERS)){
            if(isMatch(target, REGEX_UPPER_CASE)) validConditionCount++;
            if(isMatch(target, REGEX_LOWER_CASE)) validConditionCount++;
            if(isMatch(target, REGEX_NUMBER)) validConditionCount++;
            if(isMatch(target, REGEX_SPECIAL_CHARACTERS)) validConditionCount++;
        } else {
            return false;
        }

        return validConditionCount >= 3;
    }

    private static boolean isMatch(String target, String regex){
        return Pattern.matches(regex, target);
    }
}
