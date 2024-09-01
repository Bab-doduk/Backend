package com.sparta.bobdoduk.notice.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.notice.dto.InquiryReqDto;
import com.sparta.bobdoduk.notice.dto.InquiryResDto;
import com.sparta.bobdoduk.notice.service.InquiryService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<InquiryResDto> createInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody InquiryReqDto inquiryReqDto) {
        InquiryResDto createdInquiry = inquiryService.createInquiry(userDetails, inquiryReqDto);
        return ResponseEntity.ok(createdInquiry);
    }

    // 사용자 본인의 모든 문의 조회
    @GetMapping
    public ResponseEntity<List<InquiryResDto>> getUserInquiries(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<InquiryResDto> inquiries = inquiryService.getUserInquiries(userDetails);
        return ResponseEntity.ok(inquiries);
    }

    // 관리자용 모든 문의 조회
    @GetMapping("/all")
    public ResponseEntity<List<InquiryResDto>> getAllInquiries(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<InquiryResDto> inquiries = inquiryService.getAllInquiries(userDetails);
        return ResponseEntity.ok(inquiries);
    }

    // 문의 단건 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryResDto> getInquiryById(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId) {
        InquiryResDto inquiry = inquiryService.getInquiryById(userDetails, inquiryId);
        return ResponseEntity.ok(inquiry);
    }

    // 문의 수정
    @PatchMapping("/{inquiryId}")
    public ResponseEntity<InquiryResDto> updateInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId,
            @RequestBody InquiryReqDto inquiryReqDto) {
        InquiryResDto updatedInquiry = inquiryService.updateInquiry(userDetails, inquiryId, inquiryReqDto);
        return ResponseEntity.ok(updatedInquiry);
    }

    // 문의 삭제
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID inquiryId) {
        inquiryService.deleteInquiry(userDetails, inquiryId);
        return ResponseEntity.noContent().build();
    }
}
