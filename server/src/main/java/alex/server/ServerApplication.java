package alex.server;

import alex.server.role.Role;
import alex.server.user.User;
import alex.server.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setEmail("ilyass@admin.com");
            user.setPassword("adminpassword");
            user.getRoles().add(
                    new Role("USER")
            );
            user.getRoles().add(
                    new Role("SELLER")
            );
            user.getRoles().add(
                    new Role("ADMIN")
            );
            user.getRoles().add(
                    new Role("STAFF")
            );
            user.setFirstName("Ilyass");
            user.setLastName("Bougati");

            userRepository.save(user);
        };
    }

}
