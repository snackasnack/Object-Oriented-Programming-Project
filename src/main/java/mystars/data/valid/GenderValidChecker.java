package mystars.data.valid;

import mystars.data.shared.Gender;

import java.util.Arrays;

/**
 * Gender validity checker.
 */
public class GenderValidChecker implements ValidChecker {

    /**
     * Checks input validity.
     *
     * @param line Line to check.
     * @return If input is valid.
     */
    @Override
    public boolean isValid(String line) {
        return Arrays.stream(Gender.values()).map(Gender::name).anyMatch(line::equalsIgnoreCase);
    }
}
