package com.quickticket.quickticket.domain.user.service;

import com.quickticket.quickticket.domain.user.dto.UserResponse;
import com.quickticket.quickticket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponse.Details getResponseDetailsById(Long id) {
        var entity = repository.getById(id);

        return UserResponse.Details.from(entity);
    }
}
