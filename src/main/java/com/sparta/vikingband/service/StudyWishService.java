package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyWishRequestDto;
import com.sparta.vikingband.dto.StudyWishResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.StudyWish;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.MemberRoleEnum;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyRepository;
import com.sparta.vikingband.repository.StudyWishRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class StudyWishService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyWishRepository studyWishRepository;

    @Transactional
    public StudyWishResponseDto createWish(StudyWishRequestDto studyWishRequestDto,
                                             UserDetailsImpl userDetails) {
        // 인증된 사용자 이름으로 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        // 입력된 스터디 ID 정보를 DB에서 조회
        Study study = studyRepository.findById(studyWishRequestDto.getStudy().getId()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        StudyWish newStudyWish = new StudyWish(member, study);
        studyWishRepository.save(newStudyWish);

        return StudyWishResponseDto.of(newStudyWish);
    }

    @Transactional
    public void deleteWish(Long studyWishId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyWish studyWish = studyWishRepository.findById(studyWishId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.STUDYWISH_NOT_FOUND.getMessage())
        );

        if (member.getRole() != MemberRoleEnum.ADMIN && studyWish.getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        studyWishRepository.delete(studyWish);
    }
}
