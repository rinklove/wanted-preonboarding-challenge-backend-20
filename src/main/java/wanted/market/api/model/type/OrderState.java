package wanted.market.api.model.type;

public enum OrderState {

    OUTSTANDING,    //승인 대기
    APPROVED,       //판매 승인
    CONFIRMED,      //구매 확정
    CANCELED,       //구매 취소
}
