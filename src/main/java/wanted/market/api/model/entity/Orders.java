package wanted.market.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.market.api.model.type.OrderState;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_NO")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderState state;

}
