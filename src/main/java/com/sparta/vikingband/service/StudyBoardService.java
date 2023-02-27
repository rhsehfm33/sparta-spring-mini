package com.sparta.vikingband.service;

import com.sparta.vikingband.dto.StudyBoardRequestDto;
import com.sparta.vikingband.dto.StudyBoardResponseDto;
import com.sparta.vikingband.dto.StudyBoardWholeResponseDto;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.entity.StudyBoard;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.MemberRoleEnum;
import com.sparta.vikingband.repository.StudyBoardRepository;
import com.sparta.vikingband.repository.StudyRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyBoardService {
        private final StudyRepository studyRepository;
        private final StudyBoardRepository studyBoardRepository;

       @Transactional
       public StudyBoardResponseDto createStudyBoard
               (Long studyId,
                StudyBoardRequestDto requestDto,
                UserDetailsImpl userDetailsImpl)
       {
       //로그인 된 유저들이 만든 특정 스터디에 게시글을 남길 때
        Study study = studyRepository.findByIdAndMemberId(studyId, userDetailsImpl.getMember().getId()).orElseThrow(
                ()-> new EntityNotFoundException(ErrorMessage.STUDY_NOT_FOUND.getMessage())
                );

       StudyBoard studyBoard = studyBoardRepository.save(new StudyBoard(requestDto,userDetailsImpl.getMember(),study));
       return StudyBoardResponseDto.of(studyBoard);
    }

    @Transactional(readOnly = true)
    public StudyBoardResponseDto getIdStudyBoard(Long studyboardId){
           StudyBoard studyBoard = studyBoardRepository.findById(studyboardId).orElseThrow(
                   ()-> new EntityNotFoundException(ErrorMessage.STUDY_BOARD_NOT_FOUND_MESSAGE.getMessage())
           );
           return StudyBoardResponseDto.of(studyBoard);
    }

    @Transactional(readOnly = true)
    public StudyBoardWholeResponseDto getStudyBoards(){
       StudyBoardWholeResponseDto dto = new StudyBoardWholeResponseDto();
       List<StudyBoard>studyboardList = studyBoardRepository.findAll();
       for (StudyBoard studyBoard : studyboardList){
           dto.addStudyBoard(StudyBoardResponseDto.of(studyBoard));
       }
       return dto;
    }

    @Transactional
    public StudyBoardResponseDto updateStudyBoard
            (Long studyboardId,
            StudyBoardRequestDto requestDto,
            UserDetailsImpl userDetailsImpl)
    {
        StudyBoard studyBoard = getstudyboard(studyboardId, userDetailsImpl.getMember());
        studyBoard.update(requestDto);
        return StudyBoardResponseDto.of(studyBoard);
    }

    @Transactional
    public void deleteStudyBoard
            (Long studyboardId,
            UserDetailsImpl userDetailsImpl)
    {
        StudyBoard studyBoard = getstudyboard(studyboardId, userDetailsImpl.getMember());
        studyBoardRepository.deleteById(studyBoard.getId());
    }

    //매서드 추출
    private StudyBoard getstudyboard (Long studyboardId, Member member){
        return (member.getRole()== MemberRoleEnum.USER) ?
                studyBoardRepository.findByIdAndMemberId(studyboardId, member.getId()).orElseThrow(
                        ()->new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
                )
                : studyBoardRepository.findById(studyboardId).orElseThrow(
                ()->new EntityNotFoundException(ErrorMessage.STUDY_BOARD_NOT_FOUND_MESSAGE.getMessage())
        );
    }
}
