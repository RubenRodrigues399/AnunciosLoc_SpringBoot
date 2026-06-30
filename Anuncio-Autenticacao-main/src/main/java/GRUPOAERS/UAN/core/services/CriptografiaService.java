package GRUPOAERS.UAN.core.services;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class CriptografiaService {
    private static final String ALGORITHM = "AES";
    private static final byte[] SECRET_KEY_TICKET = "MySecretKey12345".getBytes(StandardCharsets.UTF_8); // Em produção, use KeyStore!

    private static final byte[] SECRET_KEY_SENHA = "MySecretSenha12345678901".getBytes();; // Em produção, use KeyStore!

    public static String encryptTicket(String data) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY_TICKET, ALGORITHM));
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decryptTicket(String encryptedData) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY_TICKET, ALGORITHM));
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public static String encryptPassword(String data) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY_SENHA, ALGORITHM));
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decryptPassword(String encryptedData) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY_SENHA, ALGORITHM));
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }
}
