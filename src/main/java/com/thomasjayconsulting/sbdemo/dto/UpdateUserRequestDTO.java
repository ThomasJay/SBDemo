package com.thomasjayconsulting.sbdemo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequestDTO {

    @Valid

    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotNull(message = "firstName is mandatory")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotNull(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    @Size(min = 2, max = 200, message = "lastName must be between 2 and 200 characters")
    private String lastName;

}
