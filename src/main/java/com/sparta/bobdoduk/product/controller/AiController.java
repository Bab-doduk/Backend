package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.product.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/ai-content")
    public ResponseEntity<ApiResponseDto<String>> generateContent(@RequestParam(name = "apiKey") String apiKey,
                                                                  @RequestParam(name = "prompt") String prompt) {

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "AI 검색 성공", aiService.createAiResponse(apiKey, prompt)));
    }

}
