package wanted.market.api.model.dto.orders;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListRequestDto {
    private long itemNo;
    private int page;
}
