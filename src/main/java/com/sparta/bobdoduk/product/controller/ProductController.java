package com.sparta.bobdoduk.product.controller;

import com.sparta.bobdoduk.product.dto.ProductRequestDTO;
import com.sparta.bobdoduk.product.dto.ProductSearchRequestDTO;
import com.sparta.bobdoduk.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

//    @GetMapping("/search")
//    public ResponseEntity<?> searchProduct(@RequestBody ProductSearchRequestDTO searchRequestDTO) {
//        return ResponseEntity.ok().body(productService.searchProduct(searchRequestDTO));
//    }

}
