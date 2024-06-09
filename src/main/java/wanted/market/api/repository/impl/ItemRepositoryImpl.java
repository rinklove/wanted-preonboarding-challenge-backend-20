package wanted.market.api.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.repository.CustomItemRepository;

import java.util.List;

import static wanted.market.api.model.entity.QItem.item;
import static wanted.market.api.model.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements CustomItemRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<ItemListResponseDto> findAll(Pageable pageable) {
        return queryFactory.select(Projections.constructor(ItemListResponseDto.class,
                item.no
                , item.name
                , item.price
                , item.quantity
                , item.state
                , member.nickname
                , item.enrollDate))
                .from(item)
                .innerJoin(member).on(item.member.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
