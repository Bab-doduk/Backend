package com.sparta.bobdoduk.notice.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.notice.dto.InquiryReqDto;
import com.sparta.bobdoduk.notice.dto.InquiryResDto;
import com.sparta.bobdoduk.notice.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<InquiryResDto>> createInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody InquiryReqDto inquiryReqDto) {

        InquiryResDto createdInquiry = inquiryService.createInquiry(userDetails, inquiryReqDto);
        ApiResponseDto<InquiryResDto> response = new ApiResponseDto<>(HttpStatus.CREATED, "문의 생성 성공", createdInquiry);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자 본인의 모든 문의 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<InquiryResDto>>> getUserInquiries(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<InquiryResDto> inquiries = inquiryService.getUserInquiries(userDetails);
        ApiResponseDto<List<InquiryResDto>> response = new ApiResponseDto<>(HttpStatus.OK, "사용자 문의 조회 성공", inquiries);
        return ResponseEntity.ok().body(response);
    }

    // 관리자용 모든 문의 조회
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<List<InquiryResDto>>> getAllInquiries(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<InquiryResDto> inquiries = inquiryService.getAllInquiries(userDetails);
        ApiResponseDto<List<InquiryResDto>> response = new ApiResponseDto<>(HttpStatus.OK, "모든 문의 조회 성공", inquiries);
        return ResponseEntity.ok().body(response);
    }


    // 문의 단건 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto<InquiryResDto>> getInquiryById(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId) {

        InquiryResDto inquiry = inquiryService.getInquiryById(userDetails, inquiryId);
        ApiResponseDto<InquiryResDto> response = new ApiResponseDto<>(HttpStatus.OK, "문의 조회 성공", inquiry);
        return ResponseEntity.ok().body(response);
    }

    // 문의 수정
    @PatchMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto<InquiryResDto>> updateInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId,
            @RequestBody InquiryReqDto inquiryReqDto) {

        InquiryResDto updatedInquiry = inquiryService.updateInquiry(userDetails, inquiryId, inquiryReqDto);
        ApiResponseDto<InquiryResDto> response = new ApiResponseDto<>(HttpStatus.OK, "문의 수정 성공", updatedInquiry);
        return ResponseEntity.ok(response);
    }

    // 문의 삭제
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId) {

        inquiryService.deleteInquiry(userDetails, inquiryId);
        ApiResponseDto<Void> response = new ApiResponseDto<>(HttpStatus.NO_CONTENT, "문의 삭제 성공", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
