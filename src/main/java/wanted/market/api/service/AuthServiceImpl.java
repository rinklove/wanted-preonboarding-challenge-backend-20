package wanted.market.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.config.jwt.JwtTokenProvider;
import wanted.market.api.model.dto.member.CustomUserInfoDto;
import wanted.market.api.model.dto.member.LoginRequestDto;
import wanted.market.api.model.entity.Member;
import wanted.market.api.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    @Override
    public String login(LoginRequestDto dto) {
        Member findMember = memberRepository.findByMemberId(dto.getId())
                .orElseThrow(() -> new IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다."));

        log.debug("입력한 패스워드 = {}, 가져온 패스워드 = {}", dto.getPassword(), findMember.getPassword());

        if(!encoder.matches(dto.getPassword(), findMember.getPassword())) {
            throw new IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto info = new CustomUserInfoDto(findMember.getMemberId()
                , findMember.getPassword()
                , findMember.getEmail()
                , findMember.getNickname(), null);
        return tokenProvider.createAccessToken(info);
    }

    @Override
    public Member findByToken(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        log.info("token = {}", token);
        String memberId = tokenProvider.getMemberId(token);
        return memberRepository.findByMemberId(memberId).orElseThrow(() ->  new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
