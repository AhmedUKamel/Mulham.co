package org.ahmedukamel.mulham.generator;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.model.Token;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.TokenType;
import org.ahmedukamel.mulham.repository.TokenRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AccountActivationTokenGenerator implements Function<User, Token> {
    final TokenRepository repository;


    @Override
    public Token apply(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setType(TokenType.ACCOUNT_ACTIVATION);
        token.setExpiration(LocalDateTime.now().plusDays(1));

        user.getTokens().add(token);
        return repository.save(token);
    }
}