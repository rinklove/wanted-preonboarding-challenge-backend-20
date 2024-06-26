package wanted.market.api.model.dto.item;


import lombok.*;
import wanted.market.api.model.dto.orders.OrderLog;
import wanted.market.api.model.type.ItemState;

import java.util.List;

@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponseDto {

    private long no;
    private String itemName;
    private long price;
    private long quantity;
    private ItemState state;
    private String stateString;
    private String nickname;
    private boolean isSeller;
    private boolean isBuyer;
    private List<OrderLog> logs;

    public ItemDetailResponseDto(long no, String itemName, long price, long quantity, ItemState state, String nickname) {
        this.no = no;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.nickname = nickname;
    }

    public void setState() {
        this.stateString = switch (this.state) {
            case SELLING -> "판매중";
            case RESERVING -> "예약중";
            default -> "판매 완료";
        };
    }

    public void verify(boolean isSeller, List<OrderLog> list) {
        this.logs = list;
        this.isSeller = isSeller;
        if(list != null && !list.isEmpty()) {
            isBuyer = true;
        }
        setState();
    }
}
