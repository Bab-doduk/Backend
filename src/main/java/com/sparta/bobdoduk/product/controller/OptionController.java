package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.product.dto.request.OptionRequestDTO;
import com.sparta.bobdoduk.product.dto.request.OptionSearchRequestDTO;
import com.sparta.bobdoduk.product.dto.response.OptionResponseDTO;
import com.sparta.bobdoduk.product.service.OptionService;
import com.sparta.bobdoduk.store.dto.response.StoreListResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;


    // 옵션 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<OptionResponseDTO>> createOption(@RequestBody OptionRequestDTO requestDTO,
                                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {


        OptionResponseDTO option = optionService.createOption(requestDTO, userDetails);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 등록 성공", option));
    }


    // 옵션 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<OptionResponseDTO>> getOption(@PathVariable(name = "id") UUID optionId) {

        OptionResponseDTO option = optionService.getOption(optionId);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 단건 조회 성공", option));
    }

    // 옵션 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<OptionResponseDTO>>> getAllOption() {

        List<OptionResponseDTO> options = optionService.getAllOption();

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 전체 조회 성공", options));

    }

    // 옵션 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<OptionResponseDTO>> updateOption(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                          @PathVariable(name = "id") UUID optionId,
                                                                          @RequestBody OptionRequestDTO requestDTO) {

        OptionResponseDTO option = optionService.updateOption(userDetails, optionId, requestDTO);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 수정 성공", option));

    }

    // 옵션 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteOption(@PathVariable(name = "id") UUID optionId) {

        optionService.deleteOption(optionId);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 삭제 성공", null));
    }

    // 옵션 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<OptionResponseDTO>>> searchOption(@RequestBody @Valid OptionSearchRequestDTO searchRequestDTO) {

        Page<OptionResponseDTO> options = optionService.searchOption(searchRequestDTO);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "옵션 검색 성공", options));
    }


}
