package wanted.market.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.model.dto.member.CustomUserDetails;
import wanted.market.api.model.dto.member.CustomUserInfoDto;
import wanted.market.api.model.entity.Member;
import wanted.market.api.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByMemberId(username).orElseThrow(() -> new IllegalStateException("해당하는 유저가 없습니다."));
        CustomUserInfoDto dto = modelMapper.map(findMember, CustomUserInfoDto.class);
        return new CustomUserDetails(dto);
    }



}
