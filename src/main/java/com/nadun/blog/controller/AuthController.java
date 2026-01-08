package com.nadun.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.request.UserReqDto;
import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.User;
import com.nadun.blog.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }

        return new ResponseEntity<>(
                Map.of(
                        "username", authentication.getName(),
                        "roles", authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()),
                HttpStatus.OK);

    }

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
    public ResponseEntity<ResponseDto> loginUser(@RequestBody UserReqDto userReqDto,
            HttpServletResponse response) {
        try {
            String token = userService.generateLoginToken(
                    userReqDto.getEmail(), userReqDto.getPassword());

            Cookie authCookie = new Cookie("AUTH_TOKEN", token);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false);
            authCookie.setPath("/");
            authCookie.setMaxAge(60 * 60); // 1 hour
            authCookie.setAttribute("SameSite", "Lax");

            response.addCookie(authCookie);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "Login successful", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logoutUser(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("AUTH_TOKEN", "");
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "Logout successful", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
