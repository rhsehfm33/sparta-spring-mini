package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.LoginRequestDto;
import com.sparta.vikingband.dto.MemberOuterResponseDto;
import com.sparta.vikingband.dto.MemberWholeResponseDto;
import com.sparta.vikingband.dto.SignupRequestDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.jwt.JwtUtil;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public MemberOuterResponseDto signup(SignupRequestDto signupRequestDto) {
        // username 중복 확인
        Optional<Member> found = memberRepository.findByMemberName(signupRequestDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException(ErrorMessage.USERNAME_DUPLICATION.getMessage());
        }

        // 사용자 Admin일 경우 authToken이 올바른지 체크
//        if (signupRequestDto.getMemberRoleEnum().equals(MemberRoleEnum.ADMIN)) {
//            if (signupRequestDto.getAdminToken() == null && !signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new EntityNotFoundException(ErrorMessage.WRONG_ADMIN_PASSWORD.getMessage());
//            }
//        }

        Member newMember = new Member(signupRequestDto);
        memberRepository.save(newMember);

        return MemberOuterResponseDto.of(newMember);
    }

    @Transactional(readOnly = true)
    public MemberOuterResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 사용자 확인
        Member member = memberRepository.findByMemberName(loginRequestDto.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.WRONG_USERNAME.getMessage())
        );

        // 비밀번호 확인
        if (!member.getPassword().equals(loginRequestDto.getPassword())){
            throw  new EntityNotFoundException(ErrorMessage.WRONG_PASSWORD.getMessage());
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getMemberName(), member.getRole()));
        return MemberOuterResponseDto.of(member);
    }

    @Transactional
    public MemberWholeResponseDto getMemberDetail(Long memberId, UserDetailsImpl userDetailsImpl) throws AccessDeniedException {
        // 사용자 확인
        Member member = memberRepository.findMemberDetailByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.WRONG_USERNAME.getMessage())
        );

        // 사용자 세부 정보는 오로지 자기자신만 확인할 수 있음
        if (member.getId() != memberId) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        return MemberWholeResponseDto.of(member);
    }
}
