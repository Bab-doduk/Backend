package com.sparta.bobdoduk.store.controller;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreCreateReqDto;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import com.sparta.bobdoduk.store.dto.request.StoreUpdateRequestDto;
import com.sparta.bobdoduk.store.dto.response.StoreListResponseDto;
import com.sparta.bobdoduk.store.dto.response.StoreResponseDto;
import com.sparta.bobdoduk.store.dto.response.StoreSearchResponseDto;
import com.sparta.bobdoduk.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * 가게 등록 (가게 주인)
     */
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponseDto<UUID>> createStore(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestBody StoreCreateReqDto request) {
        UUID userId = userDetails.getUser().getId();
        UUID storeId = storeService.createStore(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "가게 등록 성공", storeId));
    }

    /**
     * 가게 수정 (관리자, 가게 주인)
     */
    @PutMapping("/{storeId}")
    @PreAuthorize("hasRole('MASTER') or hasRole('OWNER')")
    public ResponseEntity<ApiResponseDto<Void>> updateStore(@PathVariable UUID storeId,
                                                            @RequestBody StoreUpdateRequestDto request,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();

        storeService.updateStore(storeId, request, userId, role);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "가게 수정 성공", null));
    }

    /**
     * 가게 삭제 (관리자, 가게 주인)
     */
    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasRole('MASTER') or hasRole('OWNER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteStore(@PathVariable UUID storeId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();

        storeService.deleteStore(storeId, userId, role);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "가게 삭제 성공", null));
    }

    /**
     * 가게 검색
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<StoreSearchResponseDto>>> searchStores(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID foodCategoryId,
            @RequestParam(required = false) UUID areaCategoryId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        // StoreSearchDto에 파라미터 설정
        StoreSearchDto searchDto = StoreSearchDto.builder()
                .name(name)
                .foodCategoryId(foodCategoryId)
                .areaCategoryId(areaCategoryId)
                .description(description)
                .address(address)
                .build();

        // 페이지 요청 설정
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        // 서비스 호출하여 검색 결과 가져오기
        Page<StoreSearchResponseDto> stores = storeService.searchStores(searchDto, pageRequest);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "가게 검색 성공", stores));
    }

}
