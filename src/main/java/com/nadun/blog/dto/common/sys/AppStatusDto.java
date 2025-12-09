package com.nadun.blog.dto.common.sys;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppStatusDto {
    private String status;
    @Value("${app.version}")
    private String version;
    private String timestamp;
}
