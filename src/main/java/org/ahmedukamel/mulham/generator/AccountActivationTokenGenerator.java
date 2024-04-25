package org.ahmedukamel.mulham.generator;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.TokenType;
import org.ahmedukamel.mulham.repository.TokenRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountActivationTokenGenerator implements ITokenGenerator {
    final TokenRepository repository;

    @Override
    public AccountToken apply(User user) {
        AccountToken accountToken = new AccountToken();
        accountToken.setUser(user);
        accountToken.setType(TokenType.ACCOUNT_ACTIVATION);
        accountToken.setExpiration(LocalDateTime.now().plusDays(1));

        int code = Generator.generateTokenCode();
        while (repository.existsByCode(code)) {
            code = Generator.generateTokenCode();
        }
        accountToken.setCode(code);

        repository.revokeUserTokens(user, TokenType.ACCOUNT_ACTIVATION);
        user.getAccountTokens().add(accountToken);
        return repository.save(accountToken);
    }
}