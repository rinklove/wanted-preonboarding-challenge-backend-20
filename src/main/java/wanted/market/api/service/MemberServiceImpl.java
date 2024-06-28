package wanted.market.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.model.dto.member.SignupRequestDto;
import wanted.market.api.model.entity.Member;
import wanted.market.api.model.type.RoleType;
import wanted.market.api.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signup(SignupRequestDto dto) {

        checkNull(dto);
        isDuplicated(dto);

        Member newMember = Member.builder()
                .memberId(dto.getMemberId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .role(RoleType.USER)
                .build();

        memberRepository.saveAndFlush(newMember);
        return "회원가입 성공";
    }

    private void isDuplicated(SignupRequestDto dto) {
        if(memberRepository.existsByMemberId(dto.getMemberId())) {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }
    }

    private static void checkNull(SignupRequestDto dto) {
        if(!(dto.getMemberId() != null && dto.getPassword() != null && dto.getNickname() != null &&
                dto.getEmail() != null)) {
            throw new NullPointerException("필수 입력값이 누락되어있습니다.");
        }
    }
}
