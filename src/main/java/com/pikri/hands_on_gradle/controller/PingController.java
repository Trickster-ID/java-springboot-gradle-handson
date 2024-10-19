package com.pikri.hands_on_gradle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pikri.hands_on_gradle.dto.GeneralDto;
import com.pikri.hands_on_gradle.dto.PingDto;

@RestController
@RequestMapping("/api")
public class PingController {
    @GetMapping("/ping")
    public GeneralDto ping() {
        PingDto.Response res = new PingDto.Response("pong");
        // Create a structured response
        GeneralDto response = new GeneralDto(200, "success", res);
        
        return response;
    }
}
