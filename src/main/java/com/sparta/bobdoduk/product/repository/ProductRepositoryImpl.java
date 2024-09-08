package com.sparta.bobdoduk.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.domain.QProduct;
import com.sparta.bobdoduk.product.dto.request.ProductSearchRequestDTO;
import com.sparta.bobdoduk.store.domain.QAreaCategory;
import com.sparta.bobdoduk.store.domain.QFoodCategory;
import com.sparta.bobdoduk.store.domain.QStore;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Product> findProducts(ProductSearchRequestDTO searchDto, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (StringUtils.hasText(searchDto.getQuery())) {
            whereBuilder.and(product.name.containsIgnoreCase(searchDto.getQuery()));
        }

        if (StringUtils.hasText(searchDto.getQuery())) {
            whereBuilder.and(product.description.containsIgnoreCase(searchDto.getQuery()));
        }

        JPAQuery<Product> query = queryFactory
                .selectFrom(product)
                .where(whereBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        long total = query.fetchCount();

        return new PageImpl<>(query.fetch(), pageable, total);
    }
}
