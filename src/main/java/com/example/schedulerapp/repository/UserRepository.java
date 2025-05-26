package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 요청받은 id에 해당하는 유저 조회 > 해당 유저데이터가 없을 경우 예외 처리
     * @param id 조회할 유저 ID
     * @return ID 값으로 조회한 유저 데이터
     * @throws ResponseStatusException 유저 데이터가 없을 경우 > 404 상태 코드로 예외 처리
     */
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<User> findUserByName(String username);

    /**
     * username 으로 유저 조회 > 해당 유저 데이터가 없을 경우 예외 처리
     * @param username 조회할 username
     * @return username 값으로 조회한 유저의 데이터
     * @throws ResponseStatusException 유저 데이터가 없을 경우 > 404 상태 코드로 예외 처리
     */
    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByName(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + username));
    }

    Optional<User> findUserByEmail(String email);

    /**
     * email 로 유저 조회 > 해당 유저 데이터가 없을 경우 예외 처리
     * @param email 조회할 email
     * @return email 값으로 조회한 유저 데이터
     * @throws ResponseStatusException 유저 데이터가 없을 경우 > 404 상태 코드로 예외 처리
     */
    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist email = " + email));
    }
}
