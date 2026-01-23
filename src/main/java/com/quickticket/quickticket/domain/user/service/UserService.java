package com.quickticket.quickticket.domain.user.service;

import com.quickticket.quickticket.domain.account.dto.AccountRequest;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.dto.UserResponse;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import com.quickticket.quickticket.domain.user.repository.UserRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse.Details getResponseDetailsById(Long id) throws DomainException {
        var entity = repository.getUserById(id)
                .orElseThrow(() -> new DomainException(UserErrorCode.NOT_FOUND));

        return UserResponse.Details.from(entity);
    }

    public User findUserByUsername(String username) {
        var user = repository.getByUsername(username)
                .orElseThrow(() -> new DomainException(UserErrorCode.NOT_FOUND));

        return mapper.toDomain(user);
    }

    public User signupNewUser(AccountRequest.Signup signupDto) {
        var usernameExists = repository.getByUsername(signupDto.username()).isPresent();

        if (usernameExists) {
            throw new DomainException(UserErrorCode.USERNAME_ALREADY_EXISTS);
        }

        var newUser = User.builder()
                .username(signupDto.username())
                .realName(signupDto.realName())
                .password(passwordEncoder.encode(signupDto.password()))
                .phone(signupDto.phone())
                .email(signupDto.email())
                .birthday(signupDto.birthday())
                .build();

        newUser = mapper.toDomain(repository.save(mapper.toEntity(newUser)));

        return newUser;
    }
}
