package com.sparta.bobdoduk.store.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.bobdoduk.store.domain.QAreaCategory;
import com.sparta.bobdoduk.store.domain.QFoodCategory;
import com.sparta.bobdoduk.store.domain.QStore;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Store> findStores(StoreSearchDto searchDto, Pageable pageable) {
        QStore store = QStore.store;
        QFoodCategory foodCategory = QFoodCategory.foodCategory;
        QAreaCategory areaCategory = QAreaCategory.areaCategory;

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (StringUtils.hasText(searchDto.getName())) {
            whereBuilder.and(store.name.containsIgnoreCase(searchDto.getName()));
        }
        if (searchDto.getFoodCategoryId() != null) {
            whereBuilder.and(store.foodCategory.foodCategoryId.eq(searchDto.getFoodCategoryId()));
        }
        if (searchDto.getAreaCategoryId() != null) {
            whereBuilder.and(store.areaCategory.areaCategoryId.eq(searchDto.getAreaCategoryId()));
        }
        if (StringUtils.hasText(searchDto.getDescription())) {
            whereBuilder.and(store.description.containsIgnoreCase(searchDto.getDescription()));
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            whereBuilder.and(store.address.containsIgnoreCase(searchDto.getAddress()));
        }

        QueryResults<Store> results = queryFactory
                .selectFrom(store)
                .leftJoin(store.foodCategory, foodCategory)
                .leftJoin(store.areaCategory, areaCategory)
                .where(whereBuilder)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Store> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "name":
                        orders.add(new OrderSpecifier<>(direction, QStore.store.name));
                        break;
                    case "address":
                        orders.add(new OrderSpecifier<>(direction, QStore.store.address));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }
}

