package com.thomasjayconsulting.sbdemo.service;

import com.thomasjayconsulting.sbdemo.convert.UserConverter;
import com.thomasjayconsulting.sbdemo.dto.NewUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UpdateUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UserDTO;
import com.thomasjayconsulting.sbdemo.model.User;
import com.thomasjayconsulting.sbdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDTO> getAllUsers() {
        log.info("getAllUsers Started");

        List<User> users = userRepository.findAll();

        log.info("getAllUsers len: " + users.size());

        return users.stream().map(user -> userConverter.convertUsertoUserDTO(user)).collect(Collectors.toList());

    }

    public UserDTO getUserByID(String id) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return userConverter.convertUsertoUserDTO(user);
        }

        return null;
    }

    public UserDTO getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return userConverter.convertUsertoUserDTO(user);
        }

        return null;


    }


    public UserDTO createUser(NewUserRequestDTO newUserRequestDTO) {

        log.info("createUser Started");

        User newUser = new User();

        newUser.setEmail(newUserRequestDTO.getEmail());
        newUser.setFirstName(newUserRequestDTO.getFirstName());
        newUser.setLastName(newUserRequestDTO.getLastName());
        newUser.setPassword(newUserRequestDTO.getPassword());


        log.info("createUser before save id: " + newUser.getId());

        newUser = userRepository.save(newUser);

        log.info("createUser after save id: " + newUser.getId());

        return userConverter.convertUsertoUserDTO(newUser);
    }

    public UserDTO updateUser(String id, UpdateUserRequestDTO updateUserRequestDTO) {

        log.info("updateUser Started id: " + id);

        User updatedUser = userRepository.findById(id).orElse(null);

        if (updatedUser == null) {
            return null;
        }

        updatedUser.setEmail(updateUserRequestDTO.getEmail());
        updatedUser.setFirstName(updateUserRequestDTO.getFirstName());
        updatedUser.setLastName(updateUserRequestDTO.getLastName());

        userRepository.save(updatedUser);

        return userConverter.convertUsertoUserDTO(updatedUser);

    }

    public UserDTO deleteUserById(String id) {

        User deleteUser = userRepository.findById(id).orElse(null);

        if (deleteUser == null) {
            return null;
        }


        userRepository.deleteById(id);

        return userConverter.convertUsertoUserDTO(deleteUser);

    }


}
