package com.sparta.bobdoduk.product.service;

import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.dto.ProductRequestDTO;
import com.sparta.bobdoduk.product.dto.ProductResponseDTO;
import com.sparta.bobdoduk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 생성
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        UUID product_id = UUID.randomUUID();

        Product product = Product.builder()
                .id(product_id)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .productStatus(requestDTO.getProductStatus())
                .image(requestDTO.getImage())
                .createUserId(requestDTO.getCreateUserId())
                .storeId(requestDTO.getStoreId())
                .build();

        return ProductResponseDTO.fromEntity(productRepository.save(product));
    }

    // 상품 단건 조회
    @Transactional
    public ProductResponseDTO getProduct(UUID productId) {
        return ProductResponseDTO.fromEntity(findByProductId(productId));
    }


    // 상품 전체 조회
    @Transactional
    public List<ProductResponseDTO> getAllProduct() {

        return ProductResponseDTO.fromEntityList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO requestDTO) {

        Product product = findByProductId(productId);

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setProductStatus(requestDTO.getProductStatus());
        product.setImage(requestDTO.getImage());
        product.setCreateUserId(requestDTO.getCreateUserId());
        product.setStoreId(requestDTO.getStoreId());

        return ProductResponseDTO.fromEntity(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

//    @Transactional
//    public List<ProductResponseDTO> searchProduct(ProductSearchRequestDTO searchDTO) {
//        return ProductResponseDTO.fromEntity();
//    }


    // 상품 아이디로 상품 찾는 로직
    public Product findByProductId(UUID productId) {
      return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 없습니다."));
    }





}
