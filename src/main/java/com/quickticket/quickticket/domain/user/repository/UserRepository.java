package com.quickticket.quickticket.domain.user.repository;

import com.quickticket.quickticket.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getById(Long id);
}
