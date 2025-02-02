package alex.server;

import alex.server.product.Product;
import alex.server.product.ProductRepository;
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
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, UserRepository userRepository, ProductRepository productRepository) {
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

            Product product1 = new Product();
            product1.setName("PS4 pro");
            product1.setDescription("A console for playing video games");
            product1.setPrice(6400);
            product1.setDiscount(20);

            Product product2 = new Product();
            product2.setName("Gaming monitor");
            product2.setDescription("Good for playing video games");
            product2.setPrice(1500);

            Product product3 = new Product();
            product3.setName("Bloodborne");
            product3.setDescription("a From Software video game, only for the best gamers");
            product3.setPrice(220);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);


            userRepository.save(user);
        };
    }

}
