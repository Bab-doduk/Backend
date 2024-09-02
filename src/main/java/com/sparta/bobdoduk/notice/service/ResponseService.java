package com.sparta.bobdoduk.notice.service;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.notice.domain.Inquiry;
import com.sparta.bobdoduk.notice.domain.Response;
import com.sparta.bobdoduk.notice.dto.ResponseReqDto;
import com.sparta.bobdoduk.notice.dto.ResponseResDto;
import com.sparta.bobdoduk.notice.repository.InquiryRepository;
import com.sparta.bobdoduk.notice.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final InquiryRepository inquiryRepository;

    // 응답 생성
    @Transactional
    public ResponseResDto createResponse(UserDetailsImpl userDetails, UUID inquiryId, ResponseReqDto responseReqDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

        Response response = Response.builder()
                .id(UUID.randomUUID())
                .inquiryId(inquiryId)
                .userId(userDetails.getUser().getId())
                .title(responseReqDto.getTitle())
                .content(responseReqDto.getContent())
                .build();
        response = responseRepository.save(response);

        return toDto(response);
    }

    @Transactional(readOnly = true)
    public List<ResponseResDto> getResponsesByInquiryId(UUID inquiryId) {
        List<Response> responses = responseRepository.findAllByInquiryId(inquiryId);
        if (responses.isEmpty()) {
            throw new CustomException(ErrorCode.INQUIRY_NOT_FOUND);
        }
        return responses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 응답 수정
    @Transactional
    public ResponseResDto updateResponse(UserDetailsImpl userDetails, UUID responseId, ResponseReqDto responseReqDto) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESPONSE_NOT_FOUND));

        // 작성자 또는 관리자만 수정 가능
        if (!response.getUserId().equals(userDetails.getUser().getId()) && !"master".equals(userDetails.getUser().getRole())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        response.setTitle(responseReqDto.getTitle());
        response.setContent(responseReqDto.getContent());
        response = responseRepository.save(response);

        return toDto(response);
    }

    // 응답 삭제
    @Transactional
    public void deleteResponse(UserDetailsImpl userDetails, UUID responseId) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESPONSE_NOT_FOUND));

        // 작성자 또는 관리자만 삭제 가능
        if (!response.getUserId().equals(userDetails.getUser().getId()) && !"master".equals(userDetails.getUser().getRole())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        responseRepository.delete(response);
    }

    private ResponseResDto toDto(Response response) {
        return ResponseResDto.builder()
                .responseId(response.getId())
                .userId(response.getUserId())
                .title(response.getTitle())
                .content(response.getContent())
                .build();
    }
}
