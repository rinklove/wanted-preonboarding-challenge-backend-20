package wanted.market.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.model.dto.member.LoginRequestDto;
import wanted.market.api.model.dto.member.MyPageResponseDto;
import wanted.market.api.model.dto.member.SignupRequestDto;
import wanted.market.api.service.AuthService;
import wanted.market.api.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;
    private final MemberService memberService;
    @GetMapping("/login")
    public ResponseEntity<String> getMemberToken(@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(dto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> addNewMember(@Valid @RequestBody SignupRequestDto dto) {
        log.info("dto = {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(dto));
    }

    @GetMapping("mypage")
    public ResponseEntity<MyPageResponseDto> getMyList(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.myPage(token));
    }
}
