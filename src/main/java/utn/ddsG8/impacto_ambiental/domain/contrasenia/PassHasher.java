package utn.ddsG8.impacto_ambiental.domain.contrasenia;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassHasher {
    public static String SHA_256(String string){

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                string.getBytes(StandardCharsets.UTF_8));

        return encodedhash.toString();
    }
}
