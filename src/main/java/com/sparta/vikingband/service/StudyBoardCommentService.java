package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyBoardCommentRequestDto;
import com.sparta.vikingband.dto.StudyBoardCommentResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import com.sparta.vikingband.entity.StudyBoardComment;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyBoardCommentRepository;
import com.sparta.vikingband.repository.StudyBoardRepository;
import com.sparta.vikingband.repository.StudyRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.vikingband.security.UserDetailsImpl;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardCommentService {

    private final StudyBoardCommentRepository studyBoardCommentRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public StudyBoardCommentResponseDto createStudyBoardComment(
            Long studyBoardId,
            StudyBoardCommentRequestDto requestDto,
            UserDetailsImpl userDetailsImpl
    ) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.STUDY_BOARD_NOT_FOUND_MESSAGE.getMessage())
        );

        StudyBoardComment newStudyBoardComment = new StudyBoardComment(requestDto, member, studyBoard.getStudy(), studyBoard);
        studyBoardCommentRepository.save(newStudyBoardComment);
        return StudyBoardCommentResponseDto.of(newStudyBoardComment);
    }

    @Transactional(readOnly = true)
    public List<StudyBoardCommentResponseDto> getStudyBoardComments(Long studyBoardId) {
        List<StudyBoardComment> studyBoardCommentList = studyBoardCommentRepository.findAllByStudyBoard_Id(studyBoardId);
        List<StudyBoardCommentResponseDto> studyBoardCommentResponseDtoList = studyBoardCommentList.stream()
                .map(studyBoardComment -> StudyBoardCommentResponseDto.of(studyBoardComment))
                .collect(Collectors.toList());

        return studyBoardCommentResponseDtoList;
    }


    @Transactional
    public StudyBoardCommentResponseDto updateComment(
            Long studyBoardCommentId,
            StudyBoardCommentRequestDto requestDto,
            UserDetailsImpl userDetailsImpl
    ) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyBoardComment studyBoardComment = studyBoardCommentRepository.findById(studyBoardCommentId).orElseThrow(
                // TODO: 에러 발생
        );

        // TODO: member와 studyBoardComment member가 같은지 확인

        studyBoardComment.update(requestDto);
        return StudyBoardCommentResponseDto.of(studyBoardComment);
    }


    @Transactional
    public void deleteComment(
            Long studyBoardCommentId,
            UserDetailsImpl userDetailsImpl
    ) {
        Member member = memberRepository.findByMemberName(userDetailsImpl.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        StudyBoardComment studyBoardComment = studyBoardCommentRepository.findById(studyBoardCommentId).orElseThrow(
                // TODO: 에러 발생
        );

        // TODO: member와 studyBoardComment member가 같은지 확인

        studyBoardCommentRepository.delete(studyBoardComment);
    }
}
