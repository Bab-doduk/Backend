package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.product.dto.request.OptionRequestDTO;
import com.sparta.bobdoduk.product.dto.request.OptionSearchRequestDTO;
import com.sparta.bobdoduk.product.dto.response.OptionResponseDTO;
import com.sparta.bobdoduk.product.service.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;


    // 옵션 생성
    @PostMapping
    public ResponseEntity<?> createOption(@RequestBody OptionRequestDTO requestDTO) {

        return ResponseEntity.ok().body(optionService.createOption(requestDTO));
    }

    // 옵션 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getOption(@PathVariable(name = "id") UUID optionId) {

        return ResponseEntity.ok().body(optionService.getOption(optionId));
    }

    // 옵션 전체 조회
    @GetMapping
    public ResponseEntity<?> getAllOption() {

        return ResponseEntity.ok().body(optionService.getAllOption());
    }

    // 옵션 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOption(@PathVariable(name = "id") UUID optionId,
                                           @RequestBody OptionRequestDTO requestDTO) {

        return ResponseEntity.ok().body(optionService.updateOption(optionId, requestDTO));
    }

    // 옵션 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable(name = "id") UUID optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.ok().body(null);
    }

    // 옵션 검색
    @GetMapping("/search")
    public ResponseEntity<Page<OptionResponseDTO>> searchOption(@RequestBody @Valid OptionSearchRequestDTO searchRequestDTO) {
        return ResponseEntity.ok().body(optionService.searchOption(searchRequestDTO));
    }


}
