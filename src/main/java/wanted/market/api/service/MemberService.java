package wanted.market.api.service;

import wanted.market.api.model.dto.member.SignupRequestDto;

public interface MemberService {

    String signup(SignupRequestDto dto);
}
