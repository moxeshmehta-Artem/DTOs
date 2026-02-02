package com.example.Architecture.config;

import com.example.Architecture.entity.Product;
import com.example.Architecture.entity.User;
import com.example.Architecture.repository.ProductRepository;
import com.example.Architecture.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepo, ProductRepository productRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                User user = new User();
                user.setName("John Doe");
                userRepo.save(user);
                System.out.println("Default User created: ID 1");
            }

            if (productRepo.count() == 0) {
                Product product = new Product();
                product.setName("Laptop");
                product.setPrice(1000.0);
                product.setStock(10);
                productRepo.save(product);
                System.out.println("Default Product created: ID 1");
            }
        };
    }
}
