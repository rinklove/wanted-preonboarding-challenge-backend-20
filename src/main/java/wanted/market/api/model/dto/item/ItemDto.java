package wanted.market.api.model.dto.item;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long no;

    @NotNull(message = "상품 이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "가격을 입력하세요")
    @Min(value = 100)
    private Long price;

    @NotNull(message = "상품 수량은 1이상 입력하세요")
    @Min(value = 1)
    private Long quantity;
}
