package enterprise.elroi.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import enterprise.elroi.data.model.User;
import enterprise.elroi.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminLogin implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public AdminLogin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // CEO Details from you
        createDefaultUser("edwardgrace120@gmail.com", "Founder1974", "CEO", "08137973130");

        // Director Details from you
        createDefaultUser("leokastro2016@gmail.com", "Director1993", "DIRECTOR", "08131076958");
    }

    private void createDefaultUser(String email, String password, String role, String phoneNumber) {
        if (userRepository.findByEmail(email).isEmpty()) {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            User user = new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setRole(role);
            user.setPhoneNumber(phoneNumber);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userRepository.save(user);
            System.out.println("✅ " + role + " account created: " + email);
        } else {
            System.out.println("ℹ️ " + role + " already exists.");
        }
    }
}