package wanted.market.api.model.dto.orders;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
//판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다.
public class OrderLog {
    private String seller;
    private String buyer;
    private int price;
    private int quantity;
    private String orderDate;
}
