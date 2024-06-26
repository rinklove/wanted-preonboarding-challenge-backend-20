package wanted.market.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.market.api.model.type.OrderState;

import java.time.LocalDateTime;

@Entity @Builder
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

}
