package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyWishRequestDto;
import com.sparta.vikingband.dto.StudyWishResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.StudyRegist;
import com.sparta.vikingband.entity.StudyWish;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.MemberRoleEnum;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyRepository;
import com.sparta.vikingband.repository.StudyWishRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.vikingband.enums.ErrorMessage.STUDY_REGIST_DUPLICATION;

@Service
@RequiredArgsConstructor
public class StudyWishService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyWishRepository studyWishRepository;

//    @Transactional(readOnly = true)
//    public List<StudyWishResponseDto> getWishes(Long memberId) {
//        List<StudyWish> studyWishList = studyWishRepository.findAllByMemberId(memberId);
//        return studyWishList.stream()
//            .map(StudyWishResponseDto::of)
//            .collect(Collectors.toList());
//    }

    @Transactional
    public StudyWishResponseDto toggleWish(
            Long studyId,
            UserDetailsImpl userDetails
    ) {
        // 인증된 사용자 이름으로 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        // 입력된 스터디 ID 정보를 DB에서 조회
        Study study = studyRepository.findById(studyId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        StudyWish studyWish = studyWishRepository.findByMemberAndStudy(member, study).orElse(null);
        if (studyWish == null) {
            studyWishRepository.save(new StudyWish(member, study));
            return StudyWishResponseDto.of(true);
        }
        else {
            studyWishRepository.delete(studyWish);
            return StudyWishResponseDto.of(false);
        }
    }
}
