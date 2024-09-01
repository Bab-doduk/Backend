package com.sparta.bobdoduk.notice.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.notice.dto.ResponseReqDto;
import com.sparta.bobdoduk.notice.dto.ResponseResDto;
import com.sparta.bobdoduk.notice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    // 응답 생성
    @PostMapping("/{inquiryId}")
    public ResponseEntity<ResponseResDto> createResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId,
            @RequestBody ResponseReqDto responseReqDto) {
        ResponseResDto response = responseService.createResponse(userDetails, inquiryId, responseReqDto);
        return ResponseEntity.ok(response);
    }

    // 특정 문의의 응답 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ResponseResDto> getResponseByInquiryId(@PathVariable UUID inquiryId) {
        ResponseResDto response = responseService.getResponseByInquiryId(inquiryId);
        return ResponseEntity.ok(response);
    }

    // 응답 수정
    @PutMapping("/{responseId}")
    public ResponseEntity<ResponseResDto> updateResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID responseId,
            @RequestBody ResponseReqDto responseReqDto) {
        ResponseResDto response = responseService.updateResponse(userDetails, responseId, responseReqDto);
        return ResponseEntity.ok(response);
    }

    // 응답 삭제
    @DeleteMapping("/{responseId}")
    public ResponseEntity<Void> deleteResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID responseId) {
        responseService.deleteResponse(userDetails, responseId);
        return ResponseEntity.noContent().build();
    }
}
