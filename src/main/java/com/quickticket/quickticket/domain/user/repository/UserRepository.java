package com.quickticket.quickticket.domain.user.repository;

import com.quickticket.quickticket.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> getByUsername(String username);
}
