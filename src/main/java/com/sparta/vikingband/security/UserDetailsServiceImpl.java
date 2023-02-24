package com.sparta.vikingband.security;


import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName) throws IllegalArgumentException {
       Member member = memberRepository.findByMemberName(memberName)  //이메일로 커스텀이 가능하다
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserDetailsImpl(member, member.getMemberName());
    }

}
