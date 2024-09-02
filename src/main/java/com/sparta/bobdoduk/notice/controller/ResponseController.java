package com.sparta.bobdoduk.notice.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.notice.dto.ResponseReqDto;
import com.sparta.bobdoduk.notice.dto.ResponseResDto;
import com.sparta.bobdoduk.notice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    // 응답 생성
    @PostMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto<ResponseResDto>> createResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId,
            @RequestBody ResponseReqDto responseReqDto) {

        ResponseResDto createdResponse = responseService.createResponse(userDetails, inquiryId, responseReqDto);
        ApiResponseDto<ResponseResDto> response = new ApiResponseDto<>(HttpStatus.CREATED, "응답 생성 성공", createdResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 문의의 응답 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto<List<ResponseResDto>>> getResponsesByInquiryId(@PathVariable UUID inquiryId) {

        List<ResponseResDto> responses = responseService.getResponsesByInquiryId(inquiryId);
        ApiResponseDto<List<ResponseResDto>> response = new ApiResponseDto<>(HttpStatus.OK, "응답 조회 성공", responses);
        return ResponseEntity.ok().body(response);
    }

    // 응답 수정
    @PutMapping("/{responseId}")
    public ResponseEntity<ApiResponseDto<ResponseResDto>> updateResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID responseId,
            @RequestBody ResponseReqDto responseReqDto) {

        ResponseResDto updatedResponse = responseService.updateResponse(userDetails, responseId, responseReqDto);
        ApiResponseDto<ResponseResDto> response = new ApiResponseDto<>(HttpStatus.OK, "응답 수정 성공", updatedResponse);
        return ResponseEntity.ok().body(response);
    }

    // 응답 삭제
    @DeleteMapping("/{responseId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID responseId) {

        responseService.deleteResponse(userDetails, responseId);
        ApiResponseDto<Void> response = new ApiResponseDto<>(HttpStatus.NO_CONTENT, "응답 삭제 성공", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
