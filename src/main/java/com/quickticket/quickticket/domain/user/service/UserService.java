package com.quickticket.quickticket.domain.user.service;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import com.quickticket.quickticket.domain.account.dto.AccountRequest;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.dto.UserResponse;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import com.quickticket.quickticket.domain.user.repository.UserRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse.Details getResponseDetailsById(Long id) throws DomainException {
        var entity = repository.getUserById(id)
                .orElseThrow(() -> new DomainException(UserErrorCode.NOT_FOUND));

        return UserResponse.Details.from(entity);
    }

    public User getDomainByUsername(String username) {
        var user = repository.getByUsername(username)
                .orElseThrow(() -> new DomainException(UserErrorCode.NOT_FOUND));

        return mapper.toDomain(user);
    }

    public User getDomainById(Long userId) {
        var user = repository.getReferenceById(userId);

        return mapper.toDomain(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));

        return mapper.toDomain(user);
    }

    @Transactional
    public User signupNewUser(AccountRequest.Signup signupDto) {
        var usernameExists = repository.getByUsername(signupDto.username()).isPresent();

        if (usernameExists) {
            throw new DomainException(UserErrorCode.USERNAME_ALREADY_EXISTS);
        }

        var newUser = User.builder()
                .type(AccountType.USER)
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
