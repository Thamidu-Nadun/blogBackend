package com.nadun.blog.dto.response;

import com.nadun.blog.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {
    private Integer id;
    private String username;
    private String email;
    private boolean isVerified;
    private Role role;
}
