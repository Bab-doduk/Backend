package com.sparta.bobdoduk.store.service;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.store.domain.AreaCategory;
import com.sparta.bobdoduk.store.domain.FoodCategory;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreCreateReqDto;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import com.sparta.bobdoduk.store.dto.request.StoreUpdateRequestDto;
import com.sparta.bobdoduk.store.dto.response.StoreListResponseDto;
import com.sparta.bobdoduk.store.dto.response.StoreResponseDto;
import com.sparta.bobdoduk.store.dto.response.StoreSearchResponseDto;
import com.sparta.bobdoduk.store.repository.AreaCategoryRepository;
import com.sparta.bobdoduk.store.repository.FoodCategoryRepository;
import com.sparta.bobdoduk.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final AreaCategoryRepository areaCategoryRepository;

    // 가게 전체 조회
    @Transactional(readOnly = true)
    public Page<StoreListResponseDto> listStores(Pageable pageable) {
        return storeRepository.findAll(pageable).map(store -> StoreListResponseDto.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .foodCategoryName(store.getFoodCategory().getName())
                .areaCategoryName(store.getAreaCategory().getName())
                .build());
    }

    // 가게 상세 조회
    @Transactional(readOnly = true)
    public StoreResponseDto getStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .foodCategoryName(store.getFoodCategory().getName())
                .areaCategoryName(store.getAreaCategory().getName())
                .description(store.getDescription())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .ownerId(store.getOwnerId())
                .build();
    }

    // 가게 등록
    @Transactional
    public UUID createStore(StoreCreateReqDto request, UUID ownerId) {
        Store store = Store.builder()
                .name(request.getName())
                .foodCategory(foodCategoryRepository.findFoodCategoryById(request.getFoodCategoryId()))
                .areaCategory(areaCategoryRepository.findAreaCategoryById(request.getAreaCategoryId()))
                .description(request.getDescription())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .ownerId(ownerId)
                .build();

        Store savedStore = storeRepository.save(store);
        return savedStore.getStoreId();
    }

    // 가게 수정
    @Transactional
    public void updateStore(UUID storeId, StoreUpdateRequestDto request, UUID userId, UserRoleEnum role) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        // OWNER 사용자는 자신의 가게만 수정 가능
        if (role == UserRoleEnum.OWNER && !store.getOwnerId().equals(userId)) {
            throw new CustomException(ErrorCode.STORE_UPDATE_UNAUTHORIZED);
        }

        FoodCategory foodCategory = foodCategoryRepository.findFoodCategoryById(request.getFoodCategoryId());
        AreaCategory areaCategory = areaCategoryRepository.findAreaCategoryById(request.getAreaCategoryId());

        store.update(
                request.getName(),
                foodCategory,
                areaCategory,
                request.getDescription(),
                request.getAddress(),
                request.getPhoneNumber()
        );
    }

    // 가게 삭제
    @Transactional
    public void deleteStore(UUID storeId, UUID userId, UserRoleEnum role) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        // OWNER 사용자는 자신의 가게만 삭제 가능
        if (role == UserRoleEnum.OWNER && !store.getOwnerId().equals(userId)) {
            throw new CustomException(ErrorCode.STORE_DELETE_UNAUTHORIZED);
        }
        storeRepository.delete(store);
    }

    // 가게 검색
    public Page<StoreSearchResponseDto> searchStores(StoreSearchDto searchDto, Pageable pageable) {
        // QueryDSL을 이용한 동적 검색 구현
        Page<Store> storePage = storeRepository.findStores(searchDto, pageable);

        // 결과를 StoreSearchResponseDto로 변환
        return storePage.map(store -> StoreSearchResponseDto.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .foodCategory(store.getFoodCategory().getName())
                .areaCategory(store.getAreaCategory().getName())
                .description(store.getDescription())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .build());
    }

}
