package wanted.market.api.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.entity.Orders;
import wanted.market.api.model.type.OrderState;
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
                        new CaseBuilder()
                                .when(orders.state.eq(OrderState.OUTSTANDING)).then("구매 대기중")
                                .when(orders.state.eq(OrderState.CONFIRMED)).then("구매 확정")
                                .when(orders.state.eq(OrderState.APPROVED)).then("구매 승인")
                                .otherwise("구매 취소됨"),
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
                        new CaseBuilder()
                                .when(orders.state.eq(OrderState.OUTSTANDING)).then("구매 대기중")
                                .when(orders.state.eq(OrderState.CONFIRMED)).then("구매 확정")
                                .when(orders.state.eq(OrderState.APPROVED)).then("구매 승인")
                                .otherwise("구매 취소됨"),
                        orders.orderDate))
                .from(orders)
                .innerJoin(member).on(orders.member.eq(member))
                .where(orders.item.no.eq(itemNo).and(member.nickname.eq(nickname)))
                .fetch();
    }

    /**
     * 구매 로그 가져오기
     * @param orderNo
     * @return
     */
    @Override
    public ItemPurchaseResponseDto findPurchaseLog(long orderNo) {
        return queryFactory.select(Projections.constructor(ItemPurchaseResponseDto.class,
                    orders.orderDate.stringValue()
                            .concat("_")
                            .concat(orders.no.stringValue()),
                    orders.item.name,
                    orders.price,
                    orders.quantity,
                    orders.orderDate))
                .from(orders)
                .where(orders.no.eq(orderNo))
                .fetchOne();
    }
}
