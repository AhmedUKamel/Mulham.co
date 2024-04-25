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
public class ForgetPasswordTokenGenerator implements ITokenGenerator {
    final TokenRepository repository;

    @Override
    public AccountToken apply(User user) {
        AccountToken accountToken = new AccountToken();
        accountToken.setUser(user);
        accountToken.setType(TokenType.RESET_PASSWORD);
        accountToken.setExpiration(LocalDateTime.now().plusMinutes(15));

        int code = Generator.generateTokenCode();
        while (repository.existsByCode(code)) {
            code = Generator.generateTokenCode();
        }
        accountToken.setCode(code);

        repository.revokeUserTokens(user, TokenType.RESET_PASSWORD);
        user.getAccountTokens().add(accountToken);
        return repository.save(accountToken);
    }
}