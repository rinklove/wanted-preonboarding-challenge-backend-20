package wanted.market.api.model.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class SellRequestDto {
    private long itemNo;
    private long orderNo;
    private String state;
}
