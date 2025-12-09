package com.nadun.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.request.UserReqDto;
import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.User;
import com.nadun.blog.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({ "", "/" })
    public ResponseEntity<ResponseDto> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), users), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(), user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDto(HttpStatus.NOT_FOUND.value(),
                        "User not found", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserReqDto userReqDto) {
        try {
            User savedUser = userService.saveUser(userReqDto.getName(),
                    userReqDto.getEmail(), userReqDto.getPassword());
            return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(),
                    "User created successfully", savedUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "User deleted successfully", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<ResponseDto> verifyUser(@PathVariable(required = true) Integer id,
            @RequestParam(required = true) String token) {
        try {
            boolean isVerified = userService.verifyUser(id, token);
            if (isVerified) {
                return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                        "User verified successfully", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDto(HttpStatus.BAD_REQUEST.value(),
                        "Invalid verification token", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
