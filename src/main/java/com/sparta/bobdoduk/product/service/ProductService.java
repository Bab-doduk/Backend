package com.sparta.bobdoduk.product.service;

import com.sparta.bobdoduk.product.domain.Option;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.domain.ProductOption;
import com.sparta.bobdoduk.product.dto.ProductOptionRequestDTO;
import com.sparta.bobdoduk.product.dto.ProductRequestDTO;
import com.sparta.bobdoduk.product.dto.ProductResponseDTO;
import com.sparta.bobdoduk.product.dto.ProductSearchRequestDTO;
import com.sparta.bobdoduk.product.repository.OptionRepository;
import com.sparta.bobdoduk.product.repository.ProductOptionRepository;
import com.sparta.bobdoduk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final OptionService optionService;
    private final ProductOptionRepository productOptionRepository;

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

    // 상품 검색
    @Transactional
    public Page<ProductResponseDTO> searchProduct(ProductSearchRequestDTO searchRequestDTO) {

        // 기본설정
        Sort.Direction direction = Sort.Direction.DESC;
        String sortBy = "createdAt";


        // ASC 정렬
        if (searchRequestDTO.getOrderBy().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }


        // 수정일자 설정
        if (searchRequestDTO.getSortBy().equals("updatedAt")) {
            sortBy = "updatedAt";
        }


        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(),Sort.by(direction, sortBy));


        return productRepository.searchProducts(searchRequestDTO.getQuery(), pageable)
                .map(ProductResponseDTO::fromEntity);
    }

    // 상품옵션 등록 (하나의 상품에 옵션 여러개 등록)
    @Transactional
    public void createProductOption(UUID productId, ProductOptionRequestDTO requestDTO) {
        // 주어진 productId로 Product 객체를 찾는다.
        Product product = findByProductId(productId);

        // 요청 DTO에서 옵션 ID 목록을 가져온다.
        List<UUID> optionIdList = requestDTO.getOptionIdList();

        // 옵션 ID 목록을 반복하여 각각의 옵션을 ProductOption으로 생성
        for (UUID optionId : optionIdList) {

            // 고유한 ID를 생성하여 새로운 ProductOption을 위한 UUID를 만든다.
            UUID productOption_id = UUID.randomUUID();

            // 옵션 ID로 Option 객체를 찾는다.
            Option option = optionService.findByOptionId(optionId);

            // Product와 Option을 연결하여 새로운 ProductOption 객체를 생성한다.
            ProductOption productOption = ProductOption.builder()
                    .id(productOption_id)  // 생성한 UUID를 ProductOption의 ID로 설정
                    .product(product)      // 주어진 Product 객체를 설정
                    .option(option)        // 주어진 Option 객체를 설정
                    .build();              // ProductOption 객체를 빌드하여 생성

            // 생성한 ProductOption 객체를 데이터베이스에 저장한다.
            productOptionRepository.save(productOption);
        }
    }

    // 상품옵션 삭제 (하나의 상품에 옵션 여러개 삭제)
    @Transactional
    public void deleteProductOption(UUID productId, ProductOptionRequestDTO requestDTO) {
        // 주어진 productId를 사용하여 Product 엔티티를 조회합니다.
        Product product = findByProductId(productId);

        // 요청 DTO에서 삭제할 옵션 ID 리스트를 가져옵니다.
        List<UUID> optionIdList = requestDTO.getOptionIdList();

        // 각 옵션 ID에 대해 반복문을 실행합니다.
        for (UUID optionId : optionIdList) {
            // 현재 옵션 ID로 Option 엔티티를 조회합니다.
            Option option = optionService.findByOptionId(optionId);

            // 특정 상품(productId)과 특정 옵션(optionId)에 연결된 ProductOption을 삭제합니다.
            productOptionRepository.deleteByProduct_IdAndOption_Id(product.getId(), option.getId());
        }
    }

    // 상품 아이디로 상품 찾는 로직
    public Product findByProductId(UUID productId) {
      return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 없습니다."));
    }





}
