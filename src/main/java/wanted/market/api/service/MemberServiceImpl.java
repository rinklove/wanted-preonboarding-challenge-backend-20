package wanted.market.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.model.dto.member.SignupRequestDto;
import wanted.market.api.model.entity.Member;
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

        if(memberRepository.existsByMemberId(dto.getPassword())) {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }

        Member newMember = Member.builder()
                .memberId(dto.getMemberId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .build();

        memberRepository.saveAndFlush(newMember);
        return "회원가입 성공";
    }
}
