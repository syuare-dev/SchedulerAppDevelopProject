package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<User> findUserByName(String username);

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByName(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + username));
    }

    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist email = " + email));
    }
}
