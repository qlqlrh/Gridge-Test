package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndState(Long id, User.State state);
    Optional<User> findByEmailAndState(String email, User.State state);
    List<User> findAllByEmailAndState(String email, User.State state);
    List<User> findAllByState(User.State state);

    Optional<User> findByEmail(String email);
}
