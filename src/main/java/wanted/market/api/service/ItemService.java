package wanted.market.api.service;


import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.orders.OrderListRequestDto;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.dto.orders.SellRequestDto;

import java.util.List;

public interface ItemService {

    List<ItemListResponseDto> findList(int page);

    ItemDetailResponseDto findOne(Long itemNo, String token);

    String enrollItem(String token, ItemDto dto);

    ItemPurchaseResponseDto purchase(String token, Long itemNo);

    String setState(String token, SellRequestDto dto);

    List<OrderLog> getOrders(String token, OrderListRequestDto dto);

    String update(String token, ItemDto dto);
}
