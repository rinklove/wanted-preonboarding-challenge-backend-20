package wanted.market.api.service;

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
import wanted.market.api.model.dto.item.ItemPurchaseResponseDto;
import wanted.market.api.model.dto.orders.SellRequestDto;
import wanted.market.api.model.entity.Item;
import wanted.market.api.model.entity.Member;
import wanted.market.api.model.entity.Orders;
import wanted.market.api.model.type.ItemState;
import wanted.market.api.model.type.OrderState;
import wanted.market.api.repository.ItemRepository;
import wanted.market.api.repository.OrdersRepository;
import wanted.market.api.repository.impl.ItemRepositoryImpl;
import wanted.market.api.repository.impl.OrdersRepositoryImpl;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemRepositoryImpl;
    private final OrdersRepository ordersRepository;
    private final OrdersRepositoryImpl ordersRepositoryImpl;
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
        String nickname = token != null ? tokenProvider.getNickname(manufactureToken(token)) : null;
        return itemRepositoryImpl.findById(itemNo, nickname);
    }

    @Override
    @Transactional
    public String enrollItem(String token, ItemDto dto) {
        log.info("token = {}", token);
        Item newItem = Item.enrollNew(dto, authService.findByToken(manufactureToken(token)));
        itemRepository.save(newItem);
        return "등록 완료";
    }

    /**
     * 아이템 구매
     * @param token
     * @param itemNo
     * @return
     */
    @Override
    @Transactional
    public ItemPurchaseResponseDto purchase(String token, Long itemNo) {
        Item findItem = itemRepository.findById(itemNo).orElseThrow(() -> new NoResultException("해당하는 상품이 없습니다."));
        Member findMember = authService.findByToken(manufactureToken(token));
        if(findItem.getMember().equals(findMember)) {
            throw new RuntimeException("구매자는 자신의 상품을 구매할 수 없습니다");
        }

        minusQuantity(findItem);
        updateState(findItem);

        Orders newOrders = Orders.addition(findItem, findMember);
        Orders orders = ordersRepository.saveAndFlush(newOrders);
        return ordersRepositoryImpl.findPurchaseLog(orders.getNo());
    }

    private void minusQuantity(Item findItem) {
        Long quantity = findItem.getQuantity();
        if(quantity == 0) {
            throw new IllegalStateException("구매 가능한 상품이 없습니다");
        }
        findItem.setQuantity(quantity-1);
    }

    /**
     * 판매자의 구매 확정
     * @param token
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public String setState(String token, SellRequestDto dto) {
        //해당 상품이 자신이 등록한 상품이 맞는지 확인.
        Member findMember = authService.findByToken(manufactureToken(token));
        Item findItem = itemRepositoryImpl.findById(dto.getItemNo());
        if(!findItem.getMember().equals(findMember)) {
            throw new RuntimeException("판매자가 아닙니다.");
        }
        //승인 여부 결정
        Orders findOrder = ordersRepository.findById(dto.getOrderNo()).orElseThrow(() -> new NoResultException("해당하는 주문이 없습니다"));
        findOrder.setPurchase(dto.getState());

        if(findOrder.getState().equals(OrderState.CANCELED)) {
            findItem.setQuantity(findItem.getQuantity()+1);
            updateState(findItem);
        } else if(!ordersRepositoryImpl.isExist(findItem)) {
            findItem.setState(ItemState.SOLD_OUT);
        }

        return dto.getState()+" 성공";
    }

    private void updateState(Item findItem) {
        Long quantity = findItem.getQuantity();
        if(quantity == 0) {
            findItem.setState(ItemState.RESERVING);
        } else if(!findItem.getState().equals(ItemState.SELLING)){
            findItem.setState(ItemState.SELLING);
        }
    }

    private String manufactureToken(String token) {
        return token.substring(7);
    }

    private Pageable getPageable(int page) {
        return PageRequest.of(page-1, PAGE_SIZE, Sort.Direction.DESC, "enrollDate");
    }
}
