package wanted.market.api.service;


import wanted.market.api.model.dto.member.LoginRequestDto;
import wanted.market.api.model.entity.Member;

public interface AuthService {
    String login(LoginRequestDto dto);

    Member findByToken(String token);

}
