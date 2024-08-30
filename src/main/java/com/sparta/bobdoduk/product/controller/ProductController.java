package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.product.dto.request.ProductOptionRequestDTO;
import com.sparta.bobdoduk.product.dto.request.ProductRequestDTO;
import com.sparta.bobdoduk.product.dto.response.OptionResponseDTO;
import com.sparta.bobdoduk.product.dto.response.ProductResponseDTO;
import com.sparta.bobdoduk.product.dto.request.ProductSearchRequestDTO;
import com.sparta.bobdoduk.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    // 상품 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @RequestBody ProductRequestDTO requestDTO) {

        ProductResponseDTO product = productService.createProduct(userDetails, requestDTO);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 등록 성공", product));
    }

    // 상품 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProduct(@PathVariable(name = "id") UUID productId) {

        ProductResponseDTO product = productService.getProduct(productId);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 단건 조회 성공", product));
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProduct() {

        List<ProductResponseDTO> products = productService.getAllProduct();

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 전체 조회", products));
    }

    // 상품 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails ,
                                                                            @PathVariable(name = "id") UUID productId,
                                                                            @RequestBody ProductRequestDTO requestDTO) {

        ProductResponseDTO product = productService.updateProduct(userDetails, productId, requestDTO);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 수정 성공", product));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable(name = "id") UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 삭제 성공", null));
    }

    // 상품 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<ProductResponseDTO>>> searchProduct(@RequestBody @Valid ProductSearchRequestDTO searchRequestDTO) {

        Page<ProductResponseDTO> products = productService.searchProduct(searchRequestDTO);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 검색 성공", products));
    }

    // 상품 옵션 등록
    @PostMapping("/{productId}/options")
    public ResponseEntity<ApiResponseDto<Void>> createProductOption(@PathVariable(name = "productId") UUID productId,
                                                                    @RequestBody ProductOptionRequestDTO productOptionRequestDTO) {

        productService.createProductOption(productId, productOptionRequestDTO);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 옵션 등록 성공", null));
    }

    // 상품 옵션 삭제
    @DeleteMapping("/{productId}/options")
    public ResponseEntity<ApiResponseDto<Void>> deleteProductOption(@PathVariable(name = "productId") UUID productId,
                                                                    @RequestBody ProductOptionRequestDTO productOptionRequestDTO) {

        productService.deleteProductOption(productId, productOptionRequestDTO);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "상품 옵션 삭제 성공", null));
    }

}
