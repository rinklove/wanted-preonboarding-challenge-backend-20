package wanted.market.api.service;


import wanted.market.api.model.dto.member.LoginRequestDto;

public interface AuthService {
    String login(LoginRequestDto dto);


}
