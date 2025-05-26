package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.commentDto.CommentRequestDto;
import com.example.schedulerapp.dto.commentDto.CommentResponseDto;
import com.example.schedulerapp.dto.commentDto.CommentTimeIncludeResponseDto;
import com.example.schedulerapp.entity.CommentEntity;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.CommentRepository;
import com.example.schedulerapp.repository.ScheduleRepository;
import com.example.schedulerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceJPA implements CommentService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 특정 일정에 댓글 작성 기능
     * 댓글 작성자는 로그인한 유저이며, 요청으로 전달된 댓글 내용으로 댓글을 생성한다
     * @param scheduleId 댓글 작성할 일정 ID
     * @param requestDto 작성할 댓글 내용을 담은 요청 DTO
     * @param userId 댓글 작성할 유저 ID (현재 로그인한 유저 ID)
     * @return 저장된 댓글 정보 반환
     * @throws ResponseStatusException
     *         - 유저 / 일정이 존재하지 않을 경우: 404 상태 코드로 예외 처리
     */
    @Override
    public CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto, Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        CommentEntity comment = new CommentEntity(requestDto.getComment());
        comment.setUser(findUser); // 로그인 유저 설정
        comment.setSchedule(findSchedule); // 조회한 일정 설정

        CommentEntity savedComment = commentRepository.save(comment); // 댓글 저장

        return new CommentResponseDto(savedComment.getId(), savedComment.getComment(), savedComment.getUser().getName());
    }

    /**
     * 특정 일정에 저장된 모든 댓글 조회 기능
     * @param scheduleId 댓글 조회할 일정 ID
     * @return 해당 일정에 저장된 모든 댓글 데이터 반환
     * @throws ResponseStatusException 일정이 존재하지 않을 경우 404 상태 코드로 예외 처리
     */
    @Override
    public List<CommentTimeIncludeResponseDto> findAllComments(Long scheduleId) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return findSchedule.getComments().stream().map(CommentTimeIncludeResponseDto::toDto).toList();
    }

    /**
     * 특정 일정에 저장된 댓글 수정 기능
     * 해당 댓글이 특정 일정에 속해있고, 댓글 작성자가 수정하려는 유저(로그인한 유저) 본인일 경우에만 수정 가능하도록 한다.
     * @param scheduleId 댓글이 저장된 일정 ID
     * @param commentId 수정할 댓글 ID
     * @param requestDto 수정할 댓글 내용을 담은 요청 DTO
     * @param userId 수정할 댓글 유저 ID (현재 로그인한 유저 ID)
     * @return 수정된 댓글 정보 반환
     * @throws ResponseStatusException
     *         - 일정 / 댓글이 존재하지 않을 경우 404 상태 코드로 예외 처리
     *         - 댓글이 해당 일정에 속해있지 않을 경우 400 상태 코드로 예외 처리
     *         - 댓글 작성자가 아닌 유저가 수정 시도할 경우 403 상태 코드로 예외 처리
     */
    @Transactional
    @Override
    public CommentResponseDto updateCommentById(Long scheduleId, Long commentId, CommentRequestDto requestDto, Long userId) {

        scheduleRepository.findByIdOrElseThrow(scheduleId); // 일정 조회

        CommentEntity findComment = commentRepository.findByIdCommentOrElseThrow(commentId); // 댓글 조회

        // 조회한 댓글이 조회한 일정에 속하는지 확인 절차
        if (!findComment.getSchedule().getId().equals(scheduleId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Does not exist Comment");
        }

        // 댓글 작성자가 본인인지 확인 절차
        if (!findComment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own comment.");
        }

        findComment.updateComment(requestDto.getComment());

        return new CommentResponseDto(findComment.getId(), findComment.getComment(), findComment.getUser().getName());
    }

    /**
     * 특정 일정에 저장된 댓글 삭제 기능
     * 해당 댓글이 특정 일정에 속해있고, 삭제하려는 유저(로그인한 유저)가 댓글 작성자 본인일 경우에만 삭제가 가능하도록 한다.
     * @param scheduleId 댓글이 저장된 일정 ID
     * @param commentId 삭제할 댓글 ID
     * @param userId 삭제하려는 유저 ID (현재 로그인한 유저 ID)
     * @throws ResponseStatusException
     *         - 일정 / 댓글이 존재하지 않을 경우 404 상태 코드로 예외 처리
     *         - 댓글이 해당 일정에 속해있지 않을 경우 400 상태 코드로 예외 처리
     *         - 댓글 작성자가 아닌 유저가 삭제 시도할 경우 403 상태 코드로 예외 처리
     */
    @Override
    public void deleteCommentById(Long scheduleId, Long commentId, Long userId) {

        CommentEntity findComment = commentRepository.findByIdCommentOrElseThrow(commentId); // 댓글 조회

        // 조회한 댓글이 조회한 일정에 속하는지 확인 절차
        if (!findComment.getSchedule().getId().equals(scheduleId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Does not exist Comment");
        }

        // 댓글 작성자가 본인인지 확인
        if (!findComment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only modify your own comment.");
        }

        commentRepository.delete(findComment);
    }
}
