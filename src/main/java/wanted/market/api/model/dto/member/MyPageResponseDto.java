package wanted.market.api.model.dto.member;

import lombok.*;
import wanted.market.api.model.dto.orders.MypageOrderLog;
import wanted.market.api.model.type.OrderState;

import java.util.List;

@Getter
@Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    List<MypageOrderLog> purchased;
    List<MypageOrderLog> reserved;

    public void setList(List<MypageOrderLog> logs, OrderState state) {
        if(state.equals(OrderState.APPROVED)) {
            purchased = logs;
        } else {
            reserved = logs;
        }
    }
}
