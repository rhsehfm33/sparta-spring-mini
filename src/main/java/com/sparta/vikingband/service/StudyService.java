package com.sparta.vikingband.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.vikingband.dto.*;
import com.sparta.vikingband.entity.Member;
import com.sparta.vikingband.entity.Study;
import com.sparta.vikingband.enums.ErrorMessage;
import com.sparta.vikingband.enums.SortType;
import com.sparta.vikingband.repository.MemberRepository;
import com.sparta.vikingband.repository.StudyRepository;
import com.sparta.vikingband.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    //사진 관련 메서드
    @Transactional
    public ImageURLWholeResponseDto uploadFile(List<MultipartFile> multipartFile, UserDetailsImpl userDetailsImpl) {
        List<String> fileNameList = new ArrayList<>();
        Study study = studyRepository.findByMemberId(userDetailsImpl.getMember().getId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND.getMessage())
        );

        ImageURLWholeResponseDto dto = new ImageURLWholeResponseDto();
        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            study.updateURL("https://viking-band.s3.ap-northeast-2.amazonaws.com/"+fileName);
            dto.addImageURL(study);
            //fileNameList.add(fileName);
        });

         return dto;
        //return fileNameList;
    }

    @Transactional
    public void deleteFile(String fileName, UserDetailsImpl userDetailsImpl) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }


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
        } else if (sortType.equals(SortType.CREATED_AT)) {
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
