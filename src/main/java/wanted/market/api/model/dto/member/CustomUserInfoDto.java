package wanted.market.api.model.dto.member;


import lombok.*;
import wanted.market.api.model.type.RoleType;

@Getter
@Setter
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
