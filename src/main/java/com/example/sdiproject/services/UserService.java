package com.example.sdiproject.services;

import com.example.sdiproject.DTOs.UserResponseDTO;
import com.example.sdiproject.entities.Role;
import com.example.sdiproject.entities.User;
import com.example.sdiproject.mappers.UserResponseDTOMapper;
import com.example.sdiproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserResponseDTOMapper userResponseDTOMapper;

    private final UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers(Role role){
        if (role == null)
            return userRepository.findAll().stream().map(userResponseDTOMapper).toList();
        return  userRepository.findAllByRole(role).stream().map(userResponseDTOMapper).toList();
    }

    public UserResponseDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
        return userResponseDTOMapper.apply(user);
    }

    public UserResponseDTO getUserById(int id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return userResponseDTOMapper.apply(user);
    }
}
