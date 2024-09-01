package com.sparta.bobdoduk.review.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.repository.UserRepository;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.orders.domain.Order;
import com.sparta.bobdoduk.orders.domain.OrderStatus;
import com.sparta.bobdoduk.orders.repository.OrderRepository;
import com.sparta.bobdoduk.review.domain.Review;
import com.sparta.bobdoduk.review.dto.request.ReviewCreateDto;
import com.sparta.bobdoduk.review.dto.request.ReviewUpdateDto;
import com.sparta.bobdoduk.review.dto.response.ReviewResponseDto;
import com.sparta.bobdoduk.review.repository.ReviewRepository;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(ReviewCreateDto createDto, UUID userId) {
        // 가게와 주문이 유효한지 확인
        Store store = storeRepository.findById(createDto.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
//        Order order = orderRepository.findByUserIdAndStoreIdAndOrderStatus(userId, createDto.getStoreId(), OrderStatus.completed)
//                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 리뷰 생성 및 저장
        Review review = Review.builder()
                .store(store)
                .user(user)
                .rating(createDto.getRating())
                .comment(createDto.getComment())
                .build();
        reviewRepository.save(review);

        // 평균 평점 업데이트 로직
        updateAverageRating(store, createDto.getRating());

        return ReviewResponseDto.from(review);
    }

    // 상세 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewResponseDto.from(review);
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(UUID reviewId, UUID userId, ReviewUpdateDto updateDto, UserRoleEnum role) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        // 권한 체크
        if (!review.getUser().getId().equals(userId) && role != UserRoleEnum.MASTER) {
            throw new CustomException(ErrorCode.REVIEW_UPDATE_UNAUTHORIZED);
        }
        review.updateReview(updateDto.getRating(), updateDto.getComment());
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(UUID reviewId, UUID userId, UserRoleEnum role) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId) && role != UserRoleEnum.MASTER) {
            throw new CustomException(ErrorCode.REVIEW_DELETE_UNAUTHORIZED);
        }
        reviewRepository.delete(review);
    }

    // ================ 메서드 ================
    private void updateAverageRating(Store store, int newRating) {
        long reviewCount = reviewRepository.countByStoreAndReportIsNull(store); // 신고되지 않은 리뷰 수
        Double currentAvgRating = store.getAverageRating();

        // 현재 평균 평점이 null인 경우, 0으로 초기화
        if (currentAvgRating == null) {
            currentAvgRating = 0.0;
        }

        // 새로운 평균 평점 계산
        double newAverageRating = ((currentAvgRating * reviewCount) + newRating) / (reviewCount + 1);
        store.setAverageRating(newAverageRating);

        // 가게 저장
        storeRepository.save(store);
    }



}
