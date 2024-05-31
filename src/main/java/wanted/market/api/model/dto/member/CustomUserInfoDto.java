package wanted.market.api.model.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wanted.market.api.model.type.RoleType;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserInfoDto {

    private String memberId;
    private String password;
    private String email;
    private String nickname;
    private RoleType role;
}
