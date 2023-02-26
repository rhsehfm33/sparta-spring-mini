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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

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

        StudyRegist newStudyRegist = new StudyRegist(member, study);
        studyRegistRepository.save(newStudyRegist);

        return StudyRegistResponseDto.of(newStudyRegist);
    }


    @Transactional
    public void deleteRegist(Long studyRegistId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyRegist studyRegist = studyRegistRepository.findById(studyRegistId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.REGIST_NOT_FOUND.getMessage())
        );

        if (member.getRole() != MemberRoleEnum.ADMIN && studyRegist.getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        studyRegistRepository.delete(studyRegist);
    }

    @Transactional
    public void approveRegist(Long studyRegistId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyRegist studyRegist = studyRegistRepository.findById(studyRegistId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.REGIST_NOT_FOUND.getMessage())
        );

        if (studyRegist.getStudy().getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        studyRegist.toggleAccept();
    }

    @Transactional
    public void denyRegist(Long studyRegistId, UserDetailsImpl userDetails) throws AccessDeniedException {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyRegist studyRegist = studyRegistRepository.findById(studyRegistId).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.REGIST_NOT_FOUND.getMessage())
        );

        if (member.getRole() != MemberRoleEnum.ADMIN && studyRegist.getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        studyRegistRepository.delete(studyRegist);
    }


}
