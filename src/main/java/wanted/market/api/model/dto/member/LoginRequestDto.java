package wanted.market.api.model.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class LoginRequestDto {

    private String id;
    private String password;
}
