package com.sparta.bobdoduk.store.controller;

import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreCreateReqDto;
import com.sparta.bobdoduk.store.dto.request.StoreUpdateRequestDto;
import com.sparta.bobdoduk.store.dto.response.StoreListResponseDto;
import com.sparta.bobdoduk.store.dto.response.StoreResponseDto;
import com.sparta.bobdoduk.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 전체 조회 (페이징 처리)
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<StoreListResponseDto>>> listStores(@RequestParam int page,
                                                                                 @RequestParam int size) {
        Page<StoreListResponseDto> stores = storeService.listStores(PageRequest.of(page - 1, size));
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "가게 목록 조회 성공", stores));
    }

    /**
     * 가게 상세 조회
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponseDto<StoreResponseDto>> getStore(@PathVariable UUID storeId) {
        StoreResponseDto store = storeService.getStore(storeId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "가게 상세정보 조회 성공", store));
    }

    /**
     * 가게 등록 (관리자, 가게 주인)
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<UUID>> createStore(@RequestBody StoreCreateReqDto request) {
        UUID storeId = storeService.createStore(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "가게 등록 성공", storeId));
    }

    /**
     * 가게 수정 (관리자, 가게 주인)
     */
    @PutMapping("/{storeId}")
    public ResponseEntity<ApiResponseDto<Void>> updateStore(@PathVariable UUID storeId,
                                                            @RequestBody StoreUpdateRequestDto request) {
        storeService.updateStore(storeId, request);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "가게 수정 성공", null));
    }

    /**
     * 가게 삭제 (관리자, 가게 주인)
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "가게 삭제 성공", null));
    }

}
