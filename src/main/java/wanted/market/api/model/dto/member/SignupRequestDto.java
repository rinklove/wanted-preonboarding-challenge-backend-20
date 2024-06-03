package wanted.market.api.model.dto.member;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotNull(message = "아이디는 필수 입력항목입니다.")
    private String memberId;

    @NotNull(message = "비밀번호는 필수 입력항목입니다.")
    private String password;

    @NotNull(message = "닉네임은 필수 입력항목입니다.")
    private String nickname;

    @NotNull(message = "이메일은 필수 입력항목입니다.")
    private String email;

}
