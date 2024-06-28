package wanted.market.api.model.entity;


import jakarta.persistence.*;
import lombok.*;
import wanted.market.api.model.dto.item.ItemDto;
import wanted.market.api.model.type.ItemState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Builder
@Getter @ToString(of = {"no", "name", "price", "quantity", "state", "enrollDate"})
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long no;

    @Column(name= "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name ="QUANTITY", nullable = false)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private ItemState state;

    @Column(name = "ENROLL_DATE")
    private LocalDateTime enrollDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_NO")
    private Member member;

    @OneToMany(mappedBy = "item")
    private List<Orders> ordersList = new ArrayList<>();


    public static Item enrollNew(ItemDto dto, Member member) {
        return Item.builder()
                .no(null)
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .state(ItemState.SELLING)
                .enrollDate(LocalDateTime.now())
                .member(member)
                .build();
    }
}
