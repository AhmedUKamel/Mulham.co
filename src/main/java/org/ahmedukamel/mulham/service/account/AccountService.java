package org.ahmedukamel.mulham.service.account;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.generator.AccountActivationTokenGenerator;
import org.ahmedukamel.mulham.model.Token;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.mail.AccountActivationMailSender;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    final UserRepository repository;
    final AccountActivationTokenGenerator generator;
    final AccountActivationMailSender mailSender;
    final MessageSource messageSource;

    final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    public Object resendActivationMail(String email) {
        Optional<User> optionalUser = repository.findByEmailIgnoreCaseAndProvider(email.strip(), Provider.LOCAL);

        if (optionalUser.isPresent() && !optionalUser.get().isEnabled()) {
            CompletableFuture<Token> tokenFuture = CompletableFuture.supplyAsync(() -> generator.apply(optionalUser.get()), executor);
            tokenFuture.thenAcceptAsync(mailSender::send, executor);
        }

        String message = messageSource.getMessage("successful.resend.activation.mail", null, Locale.ENGLISH);
        return new ApiResponse(true, message, null);
    }
}