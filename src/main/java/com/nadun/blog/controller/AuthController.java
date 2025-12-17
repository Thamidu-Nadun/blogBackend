package com.nadun.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.request.UserReqDto;
import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.User;
import com.nadun.blog.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserReqDto userReqDto) {
        try {
            User savedUser = userService.saveUser(userReqDto.getName(),
                    userReqDto.getEmail(), userReqDto.getPassword());
            return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(),
                    "User registered successfully", savedUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody UserReqDto userReqDto) {
        try {
            String token = userService.generateLoginToken(
                    userReqDto.getEmail(), userReqDto.getPassword());
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "Login successful", token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
