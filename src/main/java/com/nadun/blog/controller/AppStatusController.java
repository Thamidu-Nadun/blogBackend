package com.nadun.blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nadun.blog.dto.common.sys.AppStatusDto;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/status")
public class AppStatusController {
    @Value("${app.version}")
    private String appVersion;

    @GetMapping({ "", "/health" })
    public AppStatusDto getAppStatus() {
        String status = "UP";
        String timestamp = String.valueOf(System.currentTimeMillis());

        return new AppStatusDto(status, appVersion, timestamp);
    }

}
