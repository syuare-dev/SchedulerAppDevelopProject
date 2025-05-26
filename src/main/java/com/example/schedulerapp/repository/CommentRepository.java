package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * 요청받은 id에 해당하는 댓글 조회 > 댓글이 존재하지 않을 경우 예외 처리
     * @param id 조회할 댓글 ID
     * @return 해당 ID의 댓글 데이터 값 반환
     * @throws ResponseStatusException 댓글 조회 시 없을 경우 404 상태 코드로 예외 처리
     */
    default CommentEntity findByIdCommentOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist Comment Id = " + id));
    }
}
