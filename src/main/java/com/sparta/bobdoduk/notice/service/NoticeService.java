package com.sparta.bobdoduk.notice.service;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.notice.domain.Notice;
import com.sparta.bobdoduk.notice.dto.NoticeReqDto;
import com.sparta.bobdoduk.notice.dto.NoticeResDto;
import com.sparta.bobdoduk.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 생성
    @Transactional
    public NoticeResDto createNotice(UserDetailsImpl userDetails, NoticeReqDto noticeReqDto) {
        checkAdminRole(userDetails);

        Notice notice = Notice.builder()
                .id(UUID.randomUUID())
                .userId(userDetails.getUser().getId())
                .title(noticeReqDto.getTitle())
                .content(noticeReqDto.getContent())
                .build();
        notice = noticeRepository.save(notice);
        return toDto(notice);
    }

    // 모든 공지사항 조회
    @Transactional(readOnly = true)
    public List<NoticeResDto> getAllNotices() {
        return noticeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 공지사항 ID로 조회
    @Transactional(readOnly = true)
    public NoticeResDto getNoticeById(UUID noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
        return toDto(notice);
    }

    // 공지사항 수정
    @Transactional
    public NoticeResDto updateNotice(UserDetailsImpl userDetails,UUID noticeId, NoticeReqDto noticeReqDto) {
        checkAdminRole(userDetails);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        if (!notice.getUserId().equals(userDetails.getUser().getId())) {
            throw new SecurityException("사용자 정보가 일치하지 않습니다.");
        }

        notice.setTitle(noticeReqDto.getTitle());
        notice.setContent(noticeReqDto.getContent());
        notice = noticeRepository.save(notice);

        return toDto(notice);
    }

    // 공지사항 삭제
    @Transactional
    public void deleteNotice(UserDetailsImpl userDetails, UUID noticeId) {
        checkAdminRole(userDetails);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        if (!notice.getUserId().equals(userDetails.getUser().getId())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        noticeRepository.delete(notice);
    }

    private void checkAdminRole(UserDetailsImpl userDetails) {
        if (!UserRoleEnum.MASTER.getAuthority().equals(userDetails.getUser().getRole().getAuthority())) {
            throw new CustomException(ErrorCode.USER_ROLE_MISMATCH);
        }
    }

    private NoticeResDto toDto(Notice notice) {
        return NoticeResDto.builder()
                .noticeId(notice.getId())
                .userId(notice.getUserId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
