package com.sparta.vikingband.repository;

import com.sparta.vikingband.entity.StudyRegist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRegistRepository extends JpaRepository<StudyRegist, Long> {

    /**
     * 신청 테이블의 아이디로 신청 내역 찾기
     * @param id must not be {@literal null}.
     * @return
     */
    Optional<StudyRegist> findById(Long id);

    /**
     * 모든 등록 내용 가져오기
     * @return List{StudyRegister}
     */
    List<StudyRegist> findAll();

    /**
     * 모든 등록 내용 가져오기
     * @param pageable
     * @return Page{StudyRegister}
     */
    Page<StudyRegist> findAll(Pageable pageable);

    /**
     * 회원이 가입 신청한 스터디 목록 출력
     * @param memberId
     * @return
     */
    List<StudyRegist> findAllById(Long memberId);

//    Page<StudyRegister> findAllById(Pageable pageable);   // TODO: compile에러 잡기

}

