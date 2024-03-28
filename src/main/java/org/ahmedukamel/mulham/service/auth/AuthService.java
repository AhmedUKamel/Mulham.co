package org.ahmedukamel.mulham.service.auth;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.request.LoginRequest;
import org.ahmedukamel.mulham.dto.request.RegistrationRequest;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.generator.AccountActivationTokenGenerator;
import org.ahmedukamel.mulham.model.Token;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.mail.AccountActivationMailSender;
import org.ahmedukamel.mulham.util.JsonWebTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    protected final AccountActivationTokenGenerator generator;
    protected final AccountActivationMailSender mailSender;
    protected final AuthenticationManager authManager;
    protected final PasswordEncoder passwordEncoder;
    protected final MessageSource messageSource;
    protected final UserRepository repository;

    protected final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    public Object login(Object object) {
        LoginRequest request = (LoginRequest) object;

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        String token = "";
        if (authentication.getPrincipal() instanceof User user)
            token = JsonWebTokenUtils.generateToken(user);

        String message = messageSource.getMessage("successful.login", null, Locale.ENGLISH);
        return new ApiResponse(true, message, Map.of("token", token));
    }

    @Override
    public Object register(Object object) {
        RegistrationRequest request = (RegistrationRequest) object;
        User user = new User();

        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);
        CompletableFuture<Token> tokenFuture = CompletableFuture.supplyAsync(() -> generator.apply(user));
        tokenFuture.thenAcceptAsync(mailSender::send, executor);

        String message = messageSource.getMessage("successful.register", null, Locale.ENGLISH);
        return new ApiResponse(true, message, null);
    }
}