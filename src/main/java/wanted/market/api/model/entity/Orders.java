package wanted.market.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.market.api.model.type.OrderState;

import java.time.LocalDateTime;

@Entity @Builder
@Getter
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "QUANTITY")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_NO")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @Column(name = "PURCHASE_DATE")
    private LocalDateTime purchaseDate;

    public static Orders addition(Item item, Member buyer) {
        return Orders.builder()
                .no(null)
                .price(item.getPrice())
                .quantity(1L)
                .item(item)
                .member(buyer)
                .state(OrderState.OUTSTANDING)
                .orderDate(LocalDateTime.now())
                .purchaseDate(null)
                .build();
    }

    public void setPurchase(String state) {
        this.state = getState(state);
        if(this.state.equals(OrderState.APPROVED)) {
            purchaseDate = LocalDateTime.now();
        }
    }

    private OrderState getState(String state) {
        return state.equals("승인") ? OrderState.APPROVED : OrderState.CANCELED;
    }
}
