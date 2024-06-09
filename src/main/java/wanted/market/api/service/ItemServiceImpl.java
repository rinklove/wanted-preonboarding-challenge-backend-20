package wanted.market.api.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wanted.market.api.config.jwt.JwtTokenProvider;
import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.model.dto.member.LoginRequestDto;
import wanted.market.api.model.entity.Item;
import wanted.market.api.model.entity.Member;
import wanted.market.api.repository.ItemRepository;
import wanted.market.api.repository.impl.ItemRepositoryImpl;

import java.util.ArrayList;
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
    public List<ItemListResponseDto> findList(int page) {
        Pageable pageRequest = getPageable(page);
        return itemRepositoryImpl.findAll(pageRequest);
    }

    @Override
    public ItemDetailResponseDto findOne(Long itemNo) {

        return null;
    }


    private Pageable getPageable(int page) {
        return PageRequest.of(page-1, PAGE_SIZE, Sort.Direction.DESC, "enrollDate");
    }
}
