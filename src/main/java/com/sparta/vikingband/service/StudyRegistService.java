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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class StudyRegistService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyRegistRepository studyRegistRepository;


    /**
     *
     * @param studyRegistRequestDto
     * @param userDetails
     * @return
     */
    @Transactional
    public StudyRegistResponseDto makeRegist(StudyRegistRequestDto studyRegistRequestDto, UserDetailsImpl userDetails) {

        Member member = memberRepository.findByMemberName(userDetails.getUsername()).orElseThrow(
            () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyRegistRequestDto.getStudy().getId()).orElseThrow(
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
