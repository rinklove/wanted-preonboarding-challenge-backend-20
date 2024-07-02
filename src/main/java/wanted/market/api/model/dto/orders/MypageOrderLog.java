package wanted.market.api.model.dto.orders;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
//판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다.
public class MypageOrderLog {
    private long orderNo;
    private long itemNo;
    private String itemName;
    private String seller;
    private long price;
    private long quantity;
    private String state;
    private LocalDateTime orderDate;
    private LocalDateTime purchaseDate;
}
