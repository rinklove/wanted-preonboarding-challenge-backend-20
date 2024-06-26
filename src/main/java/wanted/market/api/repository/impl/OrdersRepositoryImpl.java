package wanted.market.api.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.repository.CustomOrdersRepository;

import java.util.List;

import static wanted.market.api.model.entity.QItem.item;
import static wanted.market.api.model.entity.QMember.member;
import static wanted.market.api.model.entity.QOrders.orders;
@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements CustomOrdersRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 판매자용
     * @param itemNo
     * @return
     */
    @Override
    public List<OrderLog> findAll(Long itemNo) {
        return queryFactory.select(Projections.constructor(OrderLog.class,
                    item.member.nickname,
                    member.nickname,
                    orders.price,
                    orders.item.quantity,
                    orders.orderDate))
                .from(orders)
                .innerJoin(member).on(orders.member.eq(member))
                .where(orders.item.no.eq(itemNo))
                .fetch();
    }

    /**
     * 구매자용
     * @param itemNo
     * @param nickname
     * @return
     */
    @Override
    public List<OrderLog> findAll(Long itemNo, String nickname) {
        return queryFactory.select(Projections.constructor(OrderLog.class,
                        item.member.nickname,
                        member.nickname,
                        orders.price,
                        orders.item.quantity,
                        orders.orderDate))
                .from(orders)
                .innerJoin(member).on(orders.member.eq(member))
                .where(orders.item.no.eq(itemNo).and(member.nickname.eq(nickname)))
                .fetch();
    }
}
