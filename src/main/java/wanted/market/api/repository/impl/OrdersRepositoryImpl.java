package wanted.market.api.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.member.MyPageResponseDto;
import wanted.market.api.model.dto.orders.MypageOrderLog;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.entity.Item;
import wanted.market.api.model.entity.Member;
import wanted.market.api.model.entity.Orders;
import wanted.market.api.model.type.OrderState;
import wanted.market.api.repository.CustomOrdersRepository;

import java.util.ArrayList;
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
    public List<OrderLog> findAll(Long itemNo, Pageable pageable) {
        return queryFactory.select(Projections.constructor(OrderLog.class,
                    orders.no,
                    item.member.nickname,
                    member.nickname,
                    orders.price,
                    orders.item.quantity,
                    new CaseBuilder()
                            .when(orders.state.eq(OrderState.OUTSTANDING)).then("구매 대기중")
                            .when(orders.state.eq(OrderState.APPROVED)).then("구매 승인")
                            .otherwise("구매 취소됨"),
                    orders.orderDate,
                    orders.purchaseDate))
                .from(orders)
                .innerJoin(member).on(orders.member.eq(member))
                .where(orders.item.no.eq(itemNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 구매자용
     * @param itemNo
     * @param nickname
     * @return
     */
    @Override
    public List<OrderLog> findAll(Long itemNo, String nickname, Pageable pageable) {
        return queryFactory.select(Projections.constructor(OrderLog.class,
                        orders.no,
                        item.member.nickname,
                        member.nickname,
                        orders.price,
                        orders.item.quantity,
                        new CaseBuilder()
                                .when(orders.state.eq(OrderState.OUTSTANDING)).then("구매 대기중")
                                .when(orders.state.eq(OrderState.APPROVED)).then("구매 승인")
                                .otherwise("구매 취소됨"),
                        orders.orderDate,
                        orders.purchaseDate))
                .from(orders)
                .innerJoin(member).on(orders.member.eq(member))
                .where(orders.item.no.eq(itemNo).and(member.nickname.eq(nickname)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 마이페이지용 주문 내역 조회
     * @param loginMember
     * @param
     * @return
     */
    @Override
    public MyPageResponseDto findAll(Member loginMember) {
        OrderState[] states = {OrderState.APPROVED, OrderState.OUTSTANDING};
        MyPageResponseDto dto = new MyPageResponseDto();
        for (OrderState state : states) {
            List<MypageOrderLog> find = queryFactory.select(Projections.constructor(MypageOrderLog.class,
                            orders.no,
                            item.no,
                            item.name,
                            item.member.nickname,
                            orders.price,
                            orders.item.quantity,
                            new CaseBuilder()
                                    .when(orders.state.eq(OrderState.OUTSTANDING)).then("구매 대기중")
                                    .when(orders.state.eq(OrderState.APPROVED)).then("구매 승인")
                                    .otherwise("구매 취소됨"),
                            orders.orderDate,
                            orders.purchaseDate))
                    .from(orders)
                    .innerJoin(item).on(orders.item.eq(item))
                    .where(orders.member.eq(loginMember).and(orders.state.eq(state)))
                    .orderBy(orders.orderDate.desc())
                    .limit(10)
                    .fetch();
            dto.setList(find, state);
        }
        return dto;
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

    /**
     * 주문 기록 조회
     * @param orderNo
     * @return
     */
    @Override
    public Orders findById(Long orderNo) {
        return queryFactory.select(Projections.constructor(Orders.class,
                        orders.no,
                        orders.price,
                        orders.quantity,
                        orders.item,
                        orders.member,
                        orders.state,
                        orders.orderDate,
                        orders.purchaseDate))
                .from(orders)
                .where(orders.no.eq(orderNo))
                .fetchOne();
    }

    /**
     * 구매 확정되지 않은 상품이 존재하는 지
     * @param item
     * @return
     */
    @Override
    public boolean isExist(Item item) {
        Integer result = queryFactory.selectOne()
                .from(orders)
                .where(orders.item.eq(item).and(orders.state.eq(OrderState.OUTSTANDING)))
                .fetchFirst();
        return result != null;
    }



}
