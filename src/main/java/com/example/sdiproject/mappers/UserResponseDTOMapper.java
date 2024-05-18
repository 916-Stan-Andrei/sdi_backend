package com.example.sdiproject.mappers;


import com.example.sdiproject.DTOs.UserResponseDTO;
import com.example.sdiproject.entities.User;
import com.example.sdiproject.services.UserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseDTOMapper implements Function<User, UserResponseDTO> {

    @Override
    public UserResponseDTO apply(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
