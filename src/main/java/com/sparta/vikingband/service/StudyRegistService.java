package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyRegistRequestDto;
import com.sparta.vikingband.dto.StudyRegistResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyRegist;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.MemberRoleEnum;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyRegistRepository;
import com.sparta.vikingband.repository.StudyRepository;
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
public class StudyRegistService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyRegistRepository studyRegistRepository;

    @Transactional(readOnly = true)
    public List<StudyRegistResponseDto> getRegists(Long memberId) {
        List<StudyRegist> studyRegistList = studyRegistRepository.findAllById(memberId);

        return studyRegistList.stream()
            .map(StudyRegistResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public StudyRegistResponseDto makeRegist(Long studyId, UserDetailsImpl userDetails) {
        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        if (member.getId() == study.getMember().getId()) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_SELF_REGIST.getMessage());
        }

        StudyRegist studyRegist = studyRegistRepository.findByMemberAndStudy(member, study).orElse(null);
        if (studyRegist == null) {
            studyRegist = new StudyRegist(member, study);
            studyRegistRepository.save(studyRegist);
        }
        else {
            throw new IllegalIdentifierException(STUDY_REGIST_DUPLICATION.getMessage());
        }

        return StudyRegistResponseDto.of(studyRegist);
    }


    @Transactional
    public void deleteRegist(Long studyId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        StudyRegist studyRegist = studyRegistRepository.findByMemberAndStudy(member, study).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_REGIST_NOT_FOUND.getMessage())
        );

        studyRegistRepository.delete(studyRegist);
    }

    @Transactional
    public void approveRegist(Long studyId, Long registMemberId, UserDetailsImpl userDetails) throws AccessDeniedException {
        Member ownerMember = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Member registMember = memberRepository.findById(registMemberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        if (ownerMember.getId() != study.getMember().getId()) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        StudyRegist studyRegist = studyRegistRepository.findByMemberAndStudy(registMember, study).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_REGIST_NOT_FOUND.getMessage())
        );

        studyRegist.approve();
    }

    @Transactional
    public void denyRegist(Long studyId, Long registMemberId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member ownerMember = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Member registMember = memberRepository.findById(registMemberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        if (ownerMember.getId() != study.getMember().getId()) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        StudyRegist studyRegist = studyRegistRepository.findByMemberAndStudy(registMember, study).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_REGIST_NOT_FOUND.getMessage())
        );

        studyRegistRepository.delete(studyRegist);
    }


}
