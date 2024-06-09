package wanted.market.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.model.dto.item.ItemDetailResponseDto;
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
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ItemListResponseDto>> getList(@RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findList(page));
    }


    @GetMapping("/{itemNo}")
    public ResponseEntity<ItemDetailResponseDto> getOne(@PathVariable Long itemNo) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findOne(itemNo));
    }
}
