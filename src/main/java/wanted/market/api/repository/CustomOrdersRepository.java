package wanted.market.api.repository;

import org.springframework.data.domain.Pageable;
import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.member.MyPageResponseDto;
import wanted.market.api.model.dto.orders.MypageOrderLog;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.entity.Item;
import wanted.market.api.model.entity.Member;
import wanted.market.api.model.entity.Orders;

import java.util.List;

public interface CustomOrdersRepository {

    List<OrderLog> findAll(Long itemNo, Pageable pageable);                    //구매자용
    List<OrderLog> findAll(Long itemNo, String memberId, Pageable pageable);   //판매자용
    MyPageResponseDto findAll(Member member);   //공통
    ItemPurchaseResponseDto findPurchaseLog(long orderNo);

    Orders findById(Long orderNo);

    boolean isExist(Item item);
}
