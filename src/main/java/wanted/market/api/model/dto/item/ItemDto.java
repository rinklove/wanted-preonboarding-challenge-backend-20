package wanted.market.api.model.dto.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String name;
    private long price;
    private long quantity;
}
