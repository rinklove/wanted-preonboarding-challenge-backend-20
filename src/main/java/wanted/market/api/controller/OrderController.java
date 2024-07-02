package wanted.market.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.model.dto.orders.OrderListRequestDto;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ItemService itemService;

    @GetMapping("/list")
    ResponseEntity<List<OrderLog>> getMoreList(@RequestBody OrderListRequestDto dto,
                                               @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getOrders(token, dto));
    }
}
