package mystars.data.password;

import mystars.data.exception.MyStarsException;
import mystars.parser.Parser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Handles passwords.
 */
public class PasswordHandler {

    /**
     * Hash error message.
     */
    private static final String HASH_ERROR = "Hashing issue!";

    /**
     * Number of PBKDF2 iterations (for hashing).
     */
    private static final int PBKDF2_ITERATIONS = 32768;

    /**
     * Key length.
     */
    private static final int KEY_LENGTH = 16;

    /**
     * Generates hash as byte array.
     *
     * @param password Password to hash.
     * @param salt     Salt to use.
     * @param bytes    Number of bytes to be used.
     * @return Hashed password.
     * @throws MyStarsException If there is issue hashing password.
     */
    private byte[] generatePBKDF2(char[] password, byte[] salt, int bytes) throws MyStarsException {
        KeySpec spec = new PBEKeySpec(password, salt, PasswordHandler.PBKDF2_ITERATIONS, bytes * 8);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new MyStarsException(HASH_ERROR);
        }
    }

    /**
     * Compares 2 password hashes in constant time.
     * This will not give the hacker information of how long it takes to hash, masking the length of password.
     *
     * @param hash1 First hash.
     * @param hash2 Second hash.
     * @return If hashes are equal.
     */
    private boolean slowEquals(byte[] hash1, byte[] hash2) {
        int diff = hash1.length ^ hash2.length;
        for (int i = 0; i < hash1.length && i < hash2.length; i++) {
            diff |= hash1[i] ^ hash2[i];
        }

        return diff == 0;
    }

    /**
     * Generates and returns hash and salt string.
     *
     * @param password Password to generate hash and salt.
     * @return Hash and salt, separated by tilde.
     * @throws MyStarsException If there is hashing issue.
     */
    public String generatePBKDF2String(char[] password) throws MyStarsException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[KEY_LENGTH];
        random.nextBytes(salt);
        byte[] hash = generatePBKDF2(password, salt, KEY_LENGTH);
        return Base64.getEncoder().encodeToString(salt) + Parser.TILDE_SEPARATOR
                + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Check if password equals to the hash.
     *
     * @param password Password to be compared.
     * @param goodHash Hash to be compared.
     * @return True if equal, false otherwise.
     * @throws MyStarsException If there is issue hashing password.
     */
    public boolean validatePassword(char[] password, char[] goodHash) throws MyStarsException {
        String[] params = String.valueOf(goodHash).split(Parser.TILDE_SEPARATOR);
        byte[] salt = Base64.getDecoder().decode(params[0]);
        byte[] hash = Base64.getDecoder().decode(params[1]);
        byte[] testHash = generatePBKDF2(password, salt, hash.length);
        return slowEquals(hash, testHash);
    }
}
