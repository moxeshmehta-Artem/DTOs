package com.example.Architecture.service;

import com.example.Architecture.dto.UserRequestDTO;
import com.example.Architecture.dto.UserResponseDTO;
import com.example.Architecture.entity.User;
import com.example.Architecture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = new User();
        user.setName(request.getName());

        User savedUser = userRepo.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName());
    }
}
