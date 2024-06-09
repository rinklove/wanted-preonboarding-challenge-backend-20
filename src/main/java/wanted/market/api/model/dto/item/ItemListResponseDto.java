package wanted.market.api.model.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wanted.market.api.model.type.ItemState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static wanted.market.api.model.type.ItemState.RESERVING;
import static wanted.market.api.model.type.ItemState.SELLING;

@Getter @ToString
@NoArgsConstructor
public class ItemListResponseDto {
    private long no;    //상품 번호
    private String itemName; //상품 이름
    private long price;  //가격
    private long quantity;
    private String state;
    private String sellerUser;
    private String enrollDate;

    public ItemListResponseDto(long no, String itemName, long price, long quantity, ItemState state, String sellerUser, LocalDateTime enrollDate) {
        this.no = no;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.state = setState(state);
        this.sellerUser = sellerUser;
        this.enrollDate = formatEnrollDate(enrollDate);
    }

    public String setState(ItemState state) {
        return switch (state) {
            case SELLING -> "판메중";
            case RESERVING -> "예약중";
            default -> "판매 완료";
        };
    }

    public String formatEnrollDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"));
    }
}
