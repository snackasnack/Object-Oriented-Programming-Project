package mystars.data.valid;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Email validity checker.
 */
public class EmailValidChecker implements ValidChecker {

    /**
     * Checks input validity.
     *
     * @param line Line to check.
     * @return If input is valid.
     */
    @Override
    public boolean isValid(String line) {
        try {
            InternetAddress internetAddress = new InternetAddress(line);
            internetAddress.validate();
        } catch (AddressException e) {
            return false;
        }
        return new InputValidChecker().isValid(line);
    }
}
