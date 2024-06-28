package wanted.market.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.model.dto.item.ItemDetailResponseDto;
import wanted.market.api.model.dto.item.ItemDto;
import wanted.market.api.model.dto.item.ItemListResponseDto;
import wanted.market.api.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 목록 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ItemListResponseDto>> getList(@RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findList(page));
    }

    /**
     * 상품 상세 조회
     * @param itemNo
     * @param token
     * @return
     */
    @GetMapping("/{itemNo}")
    public ResponseEntity<ItemDetailResponseDto> getOne(@PathVariable Long itemNo, @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findOne(itemNo, token));
    }


    @PostMapping("/enroll")
    ResponseEntity<String> enrollItem(@RequestHeader(name = "Authorization") String token, @Valid @RequestBody ItemDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.enrollItem(token, dto));
    }
}
