package com.sparta.bobdoduk.notice.service;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.notice.domain.Inquiry;
import com.sparta.bobdoduk.notice.dto.InquiryReqDto;
import com.sparta.bobdoduk.notice.dto.InquiryResDto;
import com.sparta.bobdoduk.notice.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    // 문의 생성
    @Transactional
    public InquiryResDto createInquiry(UserDetailsImpl userDetails, InquiryReqDto inquiryReqDto) {
        Inquiry inquiry = Inquiry.builder()
                .id(UUID.randomUUID())
                .userId(userDetails.getUser().getId())
                .title(inquiryReqDto.getTitle())
                .content(inquiryReqDto.getContent())
                .build();
        inquiry = inquiryRepository.save(inquiry);
        return toDto(inquiry);
    }

    // 사용자가 자신의 모든 문의 조회
    @Transactional(readOnly = true)
    public List<InquiryResDto> getUserInquiries(UserDetailsImpl userDetails) {
        return inquiryRepository.findByUserId(userDetails.getUser().getId())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 모든 문의 조회 (관리자용)
    @Transactional(readOnly = true)
    public List<InquiryResDto> getAllInquiries(UserDetailsImpl userDetails) {
        checkAdminRole(userDetails);
        return inquiryRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 문의 ID로 조회 (사용자는 본인 문의만, 관리자는 모든 문의 조회 가능)
    @Transactional(readOnly = true)
    public InquiryResDto getInquiryById(UserDetailsImpl userDetails, UUID inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

        if (!inquiry.getUserId().equals(userDetails.getUser().getId()) && !"master".equals(userDetails.getUser().getRole())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        return toDto(inquiry);
    }

    // 문의 수정
    @Transactional
    public InquiryResDto updateInquiry(UserDetailsImpl userDetails, UUID inquiryId, InquiryReqDto inquiryReqDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

        if (!inquiry.getUserId().equals(userDetails.getUser().getId()) && !"master".equals(userDetails.getUser().getRole())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        inquiry.setTitle(inquiryReqDto.getTitle());
        inquiry.setContent(inquiryReqDto.getContent());
        inquiry = inquiryRepository.save(inquiry);

        return toDto(inquiry);
    }

    // 문의 삭제
    @Transactional
    public void deleteInquiry(UserDetailsImpl userDetails, UUID inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

        if (!inquiry.getUserId().equals(userDetails.getUser().getId()) && !"master".equals(userDetails.getUser().getRole())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        inquiryRepository.delete(inquiry);
    }

    // 관리자 권한 확인 메서드
    private void checkAdminRole(UserDetailsImpl userDetails) {
        if (!UserRoleEnum.MASTER.getAuthority().equals(userDetails.getUser().getRole().getAuthority())) {
            throw new CustomException(ErrorCode.USER_ROLE_MISMATCH);
        }
    }

    private InquiryResDto toDto(Inquiry inquiry) {
        return InquiryResDto.builder()
                .inquiryId(inquiry.getId())
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .build();
    }
}
