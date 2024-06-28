package wanted.market.api.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.config.jwt.JwtTokenProvider;
import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.model.entity.Item;
import wanted.market.api.repository.ItemRepository;
import wanted.market.api.repository.impl.ItemRepositoryImpl;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final int PAGE_SIZE = 7;

    @Override
    @Transactional(readOnly = true)
    public List<ItemListResponseDto> findList(int page) {
        Pageable pageRequest = getPageable(page);
        return itemRepositoryImpl.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDetailResponseDto findOne(Long itemNo, String token) {
        String nickname = token != null ? tokenProvider.getNickname(token.substring(7)) : null;
        return itemRepositoryImpl.findById(itemNo, nickname);
    }

    @Override
    @Transactional
    public String enrollItem(String token, ItemDto dto) {
        log.info("token = {}", token);
        token = token.substring(7);
        Item newItem = Item.enrollNew(dto, authService.findByToken(token));
        itemRepository.save(newItem);
        return "등록 완료";
    }


    private Pageable getPageable(int page) {
        return PageRequest.of(page-1, PAGE_SIZE, Sort.Direction.DESC, "enrollDate");
    }
}
