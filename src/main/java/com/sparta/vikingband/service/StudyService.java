package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyRequestDto;
import com.sparta.vikingband.dto.StudyResponseDto;
import com.sparta.vikingband.dto.StudyWholeResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.SortType;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public StudyResponseDto studyCreate(
            StudyRequestDto studyRequestDto,
            UserDetailsImpl userDetailsImpl
    ) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study newStudy = new Study(studyRequestDto, member);
        studyRepository.save(newStudy);

        return StudyResponseDto.of(newStudy);
    }

    @Transactional
    public StudyWholeResponseDto getStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        return StudyWholeResponseDto.of(study);
    }

    @Transactional(readOnly = true)
    public List<StudyResponseDto> getStudies() {
        List<Study> studyList = studyRepository.findAll();

        return studyList.stream()
            .map(StudyResponseDto::of)
            .collect(Collectors.toList());
    }

    public List<StudyResponseDto> getStudiesByQueryCondition(String keyword, SortType sortType) {
        if (sortType.equals(SortType.WISH)) {
            return studyRepository.findAllByKeywordOrderByStudyWishSetCountDesc(keyword);
        }
        else if (sortType.equals(SortType.CREATED_AT)) {
            return studyRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);
        }

        throw new IllegalArgumentException(ErrorMessage.WRONG_STUDY_QUERY_CONDITION.getMessage());
    }

    @Transactional
    public StudyResponseDto updateStudy(
            Long studyId,
            StudyRequestDto studyRequestDto,
            UserDetailsImpl userDetailsImpl
    ) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        if (study.getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        study.update(studyRequestDto);

        return StudyResponseDto.of(study);
    }

    @Transactional
    public void deleteStudy(Long studyId, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        Study study = studyRepository.findById(studyId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
        );

        if (study.getMember() != member) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED.getMessage());
        }

        studyRepository.delete(study);
    }
}
