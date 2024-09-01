package com.sparta.bobdoduk.notice.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.notice.dto.NoticeReqDto;
import com.sparta.bobdoduk.notice.dto.NoticeResDto;
import com.sparta.bobdoduk.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<NoticeResDto>> createNotice(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody NoticeReqDto noticeRequestDto) {

        NoticeResDto noticeResDto = noticeService.createNotice(userDetails, noticeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "공지사항 생성 성공", noticeResDto));
    }

    // 모든 공지사항 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<NoticeResDto>>> getAllNotices() {
        List<NoticeResDto> noticeResDtos = noticeService.getAllNotices();
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "공지사항 조회 성공", noticeResDtos));
    }

    // 공지사항 ID로 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponseDto<NoticeResDto>> getNoticeById(@PathVariable UUID noticeId) {
        NoticeResDto noticeResDto = noticeService.getNoticeById(noticeId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "공지사항 조회 성공", noticeResDto));
    }

    // 공지사항 수정
    @PatchMapping("/{noticeId}")
    public ResponseEntity<ApiResponseDto<NoticeResDto>> updateNotice(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID noticeId,
            @RequestBody NoticeReqDto noticeRequestDto) {

        NoticeResDto noticeResDto = noticeService.updateNotice(userDetails, noticeId, noticeRequestDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "공지사항 수정 성공", noticeResDto));
    }

    // 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteNotice(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID noticeId) {

        noticeService.deleteNotice(userDetails, noticeId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "공지사항 삭제 성공", null));
    }
}
