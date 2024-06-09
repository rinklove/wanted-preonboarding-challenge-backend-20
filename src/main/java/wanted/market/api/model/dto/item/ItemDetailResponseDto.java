package wanted.market.api.model.dto.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.type.ItemState;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponseDto {

    private long no;
    private String itemName;
    private int price;
    private int quantity;
    private ItemState state;
    private List<OrderLog> logs;
}
