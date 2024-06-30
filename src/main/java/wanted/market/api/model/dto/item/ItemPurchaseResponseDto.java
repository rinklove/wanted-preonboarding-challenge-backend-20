package wanted.market.api.model.dto.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaseResponseDto {
    private String orderNo;
    private String itemName;
    private long price;
    private long quantity;
    private LocalDateTime orderDate;
}
