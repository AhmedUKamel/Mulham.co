package org.ahmedukamel.mulham.service.account;

import jakarta.transaction.Transactional;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.generator.AccountActivationTokenGenerator;
import org.ahmedukamel.mulham.generator.ForgetPasswordTokenGenerator;
import org.ahmedukamel.mulham.generator.ITokenGenerator;
import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.mail.AccountActivationMailSender;
import org.ahmedukamel.mulham.service.mail.ForgetPasswordMailSender;
import org.ahmedukamel.mulham.service.mail.IMailSenderService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class AccountService implements IAccountService {
    final IMailSenderService accountMailSender;
    final IMailSenderService passwordMailSender;
    final ITokenGenerator accountGenerator;
    final ITokenGenerator passwordGenerator;
    final UserRepository repository;

    final ExecutorService executor = Executors.newFixedThreadPool(2);

    public AccountService(AccountActivationMailSender accountMailSender,
                          AccountActivationTokenGenerator accountGenerator,
                          ForgetPasswordMailSender passwordMailSender,
                          ForgetPasswordTokenGenerator passwordGenerator,
                          UserRepository repository) {
        this.accountMailSender = accountMailSender;
        this.accountGenerator = accountGenerator;
        this.passwordMailSender = passwordMailSender;
        this.passwordGenerator = passwordGenerator;
        this.repository = repository;
    }

    @Override
    public Object accountActivation(String email) {
        Optional<User> optionalUser = repository.findByEmailIgnoreCaseAndProvider(email.strip(), Provider.LOCAL);

        if (optionalUser.isPresent() && !optionalUser.get().isEnabled()) {
            CompletableFuture<AccountToken> tokenFuture = CompletableFuture.supplyAsync(() -> accountGenerator.apply(optionalUser.get()), executor);
            tokenFuture.thenAcceptAsync(accountMailSender::send, executor);
        }

        return new ApiResponse(true, "Thank you for requesting to resend your activation email. If your email address is already registered in our system, you will receive a new activation email shortly. Please check your inbox for the email from us.", "");
    }

    @Override
    public Object passwordReset(String email) {
//        Optional<User> optionalUser = repository.findByEmailIgnoreCaseAndProvider(email.strip(), Provider.LOCAL);

//        if (optionalUser.isPresent()) {
//            CompletableFuture<AccountToken> tokenFuture = CompletableFuture.supplyAsync(() -> passwordGenerator.apply(optionalUser.get()), executor);
//            tokenFuture.thenAcceptAsync(passwordMailSender::send, executor);
//        }

        return new ApiResponse(true, "Thank you for requesting to reset your password. If your email address is already registered in our system, you will receive a reset password email shortly. Please check your inbox for the email from us.", "");
    }
}