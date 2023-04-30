package com.thomasjayconsulting.sbdemo.controller;

import com.thomasjayconsulting.sbdemo.dto.NewUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UpdateUserRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.UserDTO;
import com.thomasjayconsulting.sbdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Users API")
public class UserRestController {


    @Value("${apiKey}")
    private List<String> API_KEYS;

    private UserService userService;


    private static final String BAD_API_KEY = "{\"status\":\"Authorization Failed\",\"message\":\"Invalid API Key\"}";

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Retrieve All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found zero or more Users",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))) }),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = "apikey", required = false) String apiKey) {

        log.info("getUsers Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        List<UserDTO> users = userService.getAllUsers();


        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Retrieve User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User matching this Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getUserById(@RequestHeader(value = "apikey", required = false) String apiKey, @Parameter(description = "id of User to be found") @PathVariable String id) {

        log.info("getUsers Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }


        UserDTO userDTO = userService.getUserByID(id);

        log.info("getUsers Completed");

        if (userDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
        }


    }

    @GetMapping("/userByEmail/{email}")
    @Operation(summary = "Retrieve User by Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User matching this Email",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getUserByEmail(@RequestHeader(value = "apikey", required = false) String apiKey, @Parameter(description = "email of User to be found") @PathVariable String email) {

        log.info("getUserByEmail Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }


        UserDTO userDTO = userService.getUserByEmail(email);

        log.info("getUserByEmail Completed");

        if (userDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
        }


    }


    @PostMapping("/users")
    @Operation(summary = "Create New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created new User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Failed to Create new User",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> createUser(@RequestHeader(value = "apikey", required = false) String apiKey, @Parameter(description = "New User Body Content to be created") @Valid @RequestBody NewUserRequestDTO newUserRequest) {

        log.info("createUser Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("createUser newUserRequest: " + newUserRequest);

        UserDTO userDTO =userService.createUser(newUserRequest);

        log.info("createUser Completed");

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Failed to Update User",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Id does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> updateUser(@RequestHeader(value = "apikey", required = false) String apiKey, @Parameter(description = "User Id to be updated") @PathVariable String id, @Parameter(description = "User Elements/Body Content to be updated") @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {

        log.info("updateUser Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        UserDTO userDTO = userService.updateUser(id, updateUserRequestDTO);

        if (userDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
        }


    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Deleted User",
                    content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "Failed to Delete User",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Id does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })

    public ResponseEntity<?> deleteUser(@RequestHeader(value = "apikey", required = false) String apiKey, @Parameter(description = "User Id to be deleted") @PathVariable String id) {
        // Delete the id

        log.info("deleteUser Started");

        if (apiKey == null || !API_KEYS.contains(apiKey)) {
            // return invalid key
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }


        UserDTO userDTO = userService.deleteUserById(id);

        if (userDTO!= null) {
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }


}

