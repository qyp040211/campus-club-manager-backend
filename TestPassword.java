import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

public class TestPassword {
    public static void main(String[] args) {
        String password = "123456";
        String salt = "campus_club_manager";
        String encrypted = DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted password: " + encrypted);
    }
}