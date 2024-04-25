package org.ahmedukamel.mulham.service.auth;

import jakarta.transaction.Transactional;
import org.ahmedukamel.mulham.dto.auth.*;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.profile.ProfileResponse;
import org.ahmedukamel.mulham.generator.AccountActivationTokenGenerator;
import org.ahmedukamel.mulham.generator.ITokenGenerator;
import org.ahmedukamel.mulham.mapper.profile.ProfileResponseMapper;
import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseChecker;
import org.ahmedukamel.mulham.service.mail.AccountActivationMailSender;
import org.ahmedukamel.mulham.service.mail.IMailSenderService;
import org.ahmedukamel.mulham.service.token.IAccessTokenService;
import org.ahmedukamel.mulham.service.token.JsonWebTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class AuthService implements IAuthService {
    final ITokenGenerator generator;
    final IMailSenderService mailSender;
    final AuthenticationManager authManager;
    final PasswordEncoder passwordEncoder;
    final UserRepository repository;
    final IAccessTokenService service;
    final ProfileResponseMapper mapper;

    final ExecutorService executor = Executors.newFixedThreadPool(2);

    public AuthService(AccountActivationTokenGenerator generator,
                       AccountActivationMailSender mailSender,
                       AuthenticationManager authManager,
                       PasswordEncoder passwordEncoder,
                       UserRepository repository,
                       JsonWebTokenService service, ProfileResponseMapper mapper) {
        this.generator = generator;
        this.mailSender = mailSender;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public Object login(Object object) {
        LoginRequest request = (LoginRequest) object;

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        String token = "";
        if (authentication.getPrincipal() instanceof User user) {
            token = service.generateToken(user);

            if (StringUtils.hasLength(request.deviceToken())) {
                user.getDeviceTokens().add(request.deviceToken().strip());
                repository.save(user);
            }
        }

        return new ApiResponse(true, "User have been logged in successfully.", token);
    }

    @Override
    public void logout(String accessToken, String deviceToken) {
        User user = service.getUser(accessToken);
        service.revokeToken(accessToken);

        if (StringUtils.hasLength(deviceToken)) {
            user.getDeviceTokens().remove(deviceToken);
            repository.save(user);
        }
    }

    @Override
    public Object register(Object object) {
        RegistrationRequest request = (RegistrationRequest) object;

        DatabaseChecker.unique(repository::existsByEmailIgnoreCaseAndProvider,
                request.email().strip(),
                Provider.LOCAL,
                User.class);

        User user = new User();
        BeanUtils.copyProperties(request, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (StringUtils.hasLength(request.deviceToken())) {
            user.getDeviceTokens().add(request.deviceToken().strip());
        }

        User savedUser = repository.save(user);
        ProfileResponse response = mapper.apply(savedUser);

        CompletableFuture<AccountToken> tokenFuture = CompletableFuture.supplyAsync(() -> generator.apply(user), executor);
        tokenFuture.thenAcceptAsync(mailSender::send, executor);

        return new ApiResponse(true, "User have been registered successfully.", response);
    }
}