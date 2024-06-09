package wanted.market.api.repository;

import org.springframework.data.domain.Pageable;
import wanted.market.api.model.dto.item.ItemListResponseDto;

import java.util.List;

public interface CustomItemRepository {

    List<ItemListResponseDto> findAll(Pageable pageable);
}
