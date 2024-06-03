package wanted.market.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static  org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wanted.market.api.model.dto.member.SignupRequestDto;
import wanted.market.api.model.entity.Member;
import wanted.market.api.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void 정상회원가입() {
        //given
        SignupRequestDto requestDto = new SignupRequestDto(
                "테스트유저",
                "1234",
                "testnick",
                "123@naver.com");

        // Mock 설정
        Member mockMember = Member.builder()
                .memberId("테스트유저")
                .password(encoder.encode("1234"))
                .nickname("testnick")
                .email("123@naver.com")
                .build();

        when(encoder.encode("1234")).thenReturn("encodedPassword1234");
        when(memberRepository.saveAndFlush(any(Member.class))).thenReturn(mockMember);

        //when
        String result = memberService.signup(requestDto);

        //then
        assertThat(result).isEqualTo("회원가입 성공");
        verify(memberRepository, times(1)).saveAndFlush(any(Member.class));
    }

    @Test
    void 비정상회원가입_아이디_중복() {
        //given
        SignupRequestDto requestDto1 = new SignupRequestDto(
                "테스트유저1",
                "1234",
                "testnick",
                "123@naver.com");

        SignupRequestDto requestDto2 = new SignupRequestDto(
                "테스트유저1",
                "1234",
                "testnick",
                "123@naver.com");


        when(memberService.signup(requestDto1));


        //then
        assertThrows(RuntimeException.class, () -> when(memberService.signup(requestDto2)));
        verify(memberRepository, times(1)).saveAndFlush(any(Member.class));
    }

    @Test
    void 비정상회원가입_정보_누락() {
        //given
        SignupRequestDto requestDto = new SignupRequestDto(
                "테스트유저3",
                "1234",
                null,
                "123@naver.com");


        //when & then
        assertThrows(NullPointerException.class, () -> when(memberService.signup(requestDto)));
        verify(memberRepository,times(0)).saveAndFlush(any(Member.class));
    }
}
