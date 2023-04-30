package com.thomasjayconsulting.sbdemo.convert;

import com.thomasjayconsulting.sbdemo.dto.UserDTO;
import com.thomasjayconsulting.sbdemo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertUsertoUserDTO(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }


    public User convertUserDTOtoUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        return user;
    }

}
