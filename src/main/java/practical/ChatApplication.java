package practical;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import practical.models.user.User;
import practical.services.AuthenticationService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import practical.services.UserService;

import java.util.logging.Logger;

import static practical.models.user.Role.ADMIN;


/**
 * <h1>Ex5TemplateApplication</h1>
 * <p>
 *     This class is the main class of the application.
 * </p>
 */
@RequiredArgsConstructor
@SpringBootApplication
public class ChatApplication {
    private static final Logger logger = Logger.getLogger(ChatApplication.class.getName());

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private User admin;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    /**
     * This method is used to create an admin user at the start of the application.
     * @param service The authentication service.
     * @return A command line runner.
     */
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            admin = User.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("admin-pw")
                    .role(ADMIN)
                    .build();

            if(userService.getUserByEmail(admin.getEmail()) == null){
                service.register(admin);
            }

            logger.info("Admin user created with email: " + admin.getEmail() + " and password: "+ passwordEncoder.encode("admin-pw"));
        };
    }

    /**
     * This method is used to delete the admin user at the end of the application.
     * <b>for now this function deactivated, you can uncomment the @PreDestroy annotation to auto-delete the admin when the app exit</b>
     */
//    @PreDestroy
    public void destroy() {
        try {
            userService.deleteAdmin(admin.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
