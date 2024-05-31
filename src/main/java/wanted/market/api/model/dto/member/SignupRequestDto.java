package wanted.market.api.model.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private String memberId;
    private String password;
    private String nickname;
    private String email;

}
