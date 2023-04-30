package com.thomasjayconsulting.sbdemo.service;

import com.thomasjayconsulting.sbdemo.dto.NewUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UpdateUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UserDTO;

import java.util.List;

public interface UserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUserByID(String id);

    public UserDTO getUserByEmail(String email);

    public UserDTO updateUser(String id, UpdateUserRequestDTO updateUserRequestDTO);

    public UserDTO deleteUserById(String id);

    public UserDTO createUser(NewUserRequestDTO newUser);
}
