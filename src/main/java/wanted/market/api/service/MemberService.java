package wanted.market.api.service;

import wanted.market.api.model.dto.member.MyPageResponseDto;
import wanted.market.api.model.dto.member.SignupRequestDto;

public interface MemberService {

    String signup(SignupRequestDto dto);

    MyPageResponseDto myPage(String token);
}
