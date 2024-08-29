package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.product.dto.request.ProductOptionRequestDTO;
import com.sparta.bobdoduk.product.dto.request.ProductRequestDTO;
import com.sparta.bobdoduk.product.dto.response.ProductResponseDTO;
import com.sparta.bobdoduk.product.dto.request.ProductSearchRequestDTO;
import com.sparta.bobdoduk.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    // 상품 생성
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO requestDTO) {

        return ResponseEntity.ok().body(productService.createProduct(requestDTO));
    }

    // 상품 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") UUID productId) {

        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<?> getAllProduct() {

        return ResponseEntity.ok().body(productService.getAllProduct());
    }

    // 상품 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") UUID productId,
                                           @RequestBody ProductRequestDTO requestDTO) {

        return ResponseEntity.ok().body(productService.updateProduct(productId, requestDTO));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body(null);
    }

    // 상품 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProduct(@RequestBody @Valid ProductSearchRequestDTO searchRequestDTO) {
        return ResponseEntity.ok().body(productService.searchProduct(searchRequestDTO));
    }

    // 상품 옵션 등록
    @PostMapping("/{productId}/options")
    public ResponseEntity<?> createProductOption(@PathVariable(name = "productId") UUID productId,
                                                 @RequestBody ProductOptionRequestDTO productOptionRequestDTO) {

        productService.createProductOption(productId, productOptionRequestDTO);
        return ResponseEntity.ok().body(null);
    }

    // 상품 옵션 삭제
    @DeleteMapping("/{productId}/options")
    public ResponseEntity<?> deleteProductOption(@PathVariable(name = "productId") UUID productId,
                                                 @RequestBody ProductOptionRequestDTO productOptionRequestDTO) {

        productService.deleteProductOption(productId, productOptionRequestDTO);
        return ResponseEntity.ok().body(null);
    }

}
