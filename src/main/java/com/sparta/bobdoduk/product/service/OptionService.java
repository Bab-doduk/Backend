package com.sparta.bobdoduk.product.service;

import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.product.domain.Option;
import com.sparta.bobdoduk.product.dto.request.OptionRequestDTO;
import com.sparta.bobdoduk.product.dto.request.OptionSearchRequestDTO;
import com.sparta.bobdoduk.product.dto.response.OptionResponseDTO;
import com.sparta.bobdoduk.product.repository.OptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    // 옵션 생성
    @Transactional
    public OptionResponseDTO createOption(OptionRequestDTO requestDTO) {

        UUID option_id = UUID.randomUUID();

        Option option = Option.builder()
                .id(option_id)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .productStatus(requestDTO.getProductStatus())
                .image(requestDTO.getImage())
                .createUserId(requestDTO.getCreateUserId())
                .storeId(requestDTO.getStoreId())
                .build();

        return OptionResponseDTO.fromEntity(optionRepository.save(option));
    }

    // 옵션 단건 조회
    @Transactional
    public OptionResponseDTO getOption(UUID optionId) {
        return OptionResponseDTO.fromEntity(findByOptionId(optionId));
    }


    // 옵션 전체 조회
    @Transactional
    public List<OptionResponseDTO> getAllOption() {

        return OptionResponseDTO.fromEntityList(optionRepository.findAll());
    }

    // 옵션 수정
    @Transactional
    public OptionResponseDTO updateOption(UUID optionId, OptionRequestDTO requestDTO) {

        Option option = findByOptionId(optionId);

        option.setName(requestDTO.getName());
        option.setDescription(requestDTO.getDescription());
        option.setPrice(requestDTO.getPrice());
        option.setProductStatus(requestDTO.getProductStatus());
        option.setImage(requestDTO.getImage());
        option.setCreateUserId(requestDTO.getCreateUserId());
        option.setStoreId(requestDTO.getStoreId());

        return OptionResponseDTO.fromEntity(option);
    }

    // 옵션 삭제
    @Transactional
    public void deleteOption(UUID optionId) {
        optionRepository.deleteById(optionId);
    }

    // 옵션 검색
    @Transactional
    public Page<OptionResponseDTO> searchOption(OptionSearchRequestDTO searchRequestDTO) {

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


        return optionRepository.searchOptions(searchRequestDTO.getQuery(), pageable)
                .map(OptionResponseDTO::fromEntity);
    }


    // 옵션 아이디로 옵션 찾는 로직
    public Option findByOptionId(UUID optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));
    }

}
