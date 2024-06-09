package wanted.market.api.service;


import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;

import java.util.List;

public interface ItemService {

    List<ItemListResponseDto> findList(int page);

    ItemDetailResponseDto findOne(Long itemNo);
}
