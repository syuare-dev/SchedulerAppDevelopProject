package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    default CommentEntity findByIdCommentOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist Comment Id = " + id));
    }
}
