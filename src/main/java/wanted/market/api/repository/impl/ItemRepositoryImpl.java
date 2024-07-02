package wanted.market.api.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.entity.Item;
import wanted.market.api.repository.CustomItemRepository;

import java.util.List;

import static wanted.market.api.model.entity.QItem.item;
import static wanted.market.api.model.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements CustomItemRepository {

    private final JPAQueryFactory queryFactory;
    private final OrdersRepositoryImpl ordersRepository;
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

    /**
     * select
     *      item.no
     *      , itemName
     *      , price
     *      , quantity
     *      , state
     *      , nickname
     * from item i
     * inner join member m
     * where i.no = itemNo;
     * on item.member=member
     * @param itemNo
     * @return
     */
    @Override
    public ItemDetailResponseDto findById(Long itemNo, String nickname, Pageable pageable) {
        ItemDetailResponseDto findItem = queryFactory.select(Projections.constructor(ItemDetailResponseDto.class,
                        item.no,
                        item.name,
                        item.price,
                        item.quantity,
                        item.state,
                        member.nickname))
                .from(item)
                .innerJoin(member).on(item.member.eq(member))
                .where(item.no.eq(itemNo))
                .fetchOne();

        List<OrderLog> logs = getLogs(findItem.getNickname(), nickname, itemNo, pageable);
        findItem.verify(isSeller(findItem.getNickname(), nickname), logs);
        return findItem;
    }

    /**
     * 구매 확정을 위한 상품 엔티티 조회(판매자용)
     * @param itemNo
     * @return
     */
    @Override
    public Item findById(Long itemNo) {
        return queryFactory.select(Projections.constructor(Item.class,
                    item.no,
                    item.name,
                    item.price,
                    item.quantity,
                    item.state,
                    item.enrollDate,
                    item.member))
                .from(item)
                .where(item.no.eq(itemNo))
                .fetchOne();
    }

    private boolean isSeller(String sellerNick, String memberNick) {
        return memberNick != null && sellerNick.equals(memberNick);
    }

    private List<OrderLog> getLogs(String sellerNick, String memberNick, Long itemNo, Pageable pageable) {
        boolean isMember = memberNick != null;
        if(!isMember)
            return null;

        List<OrderLog> logs = null;
        if(isSeller(sellerNick, memberNick)) {
            logs = ordersRepository.findAll(itemNo, pageable);
        } else {
            logs = ordersRepository.findAll(itemNo, memberNick, pageable);
        }
        return logs;
    }
}
