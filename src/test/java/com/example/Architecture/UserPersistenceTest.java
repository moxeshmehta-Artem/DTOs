package com.example.Architecture;

import com.example.Architecture.dto.UserRequestDTO;
import com.example.Architecture.dto.UserResponseDTO;
import com.example.Architecture.repository.UserRepository;
import com.example.Architecture.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserPersistenceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUserAndVerifyPersistence() {
        // Arrange
        UserRequestDTO request = new UserRequestDTO();
        request.setName("MoxeshPersistenceTest");

        // Act
        UserResponseDTO response = userService.createUser(request);

        // Assert Response
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

        // Assert Database Persistence
        boolean exists = userRepository.existsById(response.getId());
        assertThat(exists).as("User should be saved in the database").isTrue();

        System.out.println("Test passed: User created with ID " + response.getId() + " and found in DB.");
    }
}
