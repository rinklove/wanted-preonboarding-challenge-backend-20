package wanted.market.api.repository;

import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.entity.Orders;

import java.util.List;

public interface CustomOrdersRepository {

    List<OrderLog> findAll(Long itemNo);                    //구매자용
    List<OrderLog> findAll(Long itemNo, String memberId);   //판매자용

    ItemPurchaseResponseDto findPurchaseLog(long orderNo);
}
