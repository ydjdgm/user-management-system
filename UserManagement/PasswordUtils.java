import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    // Gut: Salt für zusätzliche Sicherheit bei der Passwortverschlüsselung
    public static String getSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Verbesserung: Kommentare könnten mehr Details zum Algorithmus enthalten
    public static String hashPassword(String password, String salt){
        String generatedPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch(NoSuchAlgorithmException e){
            // Verbesserung: Logging-System könnte anstelle von e.printStackTrace() genutzt werden
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static boolean verifyPassword(String password, String hashedPassword, String salt){
        return hashPassword(password, salt).equals(hashedPassword);
    }
}