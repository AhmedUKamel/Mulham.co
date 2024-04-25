package org.ahmedukamel.mulham.service.web;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.TokenRepository;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebViewService implements IWebViewService {
    final TokenRepository tokenRepository;
    final UserRepository userRepository;

    @Override
    public ModelAndView activateAccount(UUID tokenId, String email) {
        try {
            AccountToken accountToken = DatabaseFetcher.get(tokenRepository::findById, tokenId, AccountToken.class);
            User user = accountToken.getUser();

            if (!user.getEmail().equalsIgnoreCase(email.strip())) {
                throw new RuntimeException("Invalid token or email.");
            } else if (accountToken.isRevoked()) {
                throw new RuntimeException("This token is revoked.");
            } else if (accountToken.isUsed()) {
                throw new RuntimeException("This token is used.");
            } else if (accountToken.getExpiration().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("This token is expired.");
            } else if (user.isEnabled()) {
                throw new RuntimeException("Already activated user account.");
            }

            user.setEnabled(true);
            userRepository.save(user);

            accountToken.setUsed(true);
            tokenRepository.save(accountToken);

            return new ModelAndView("account-activation-page");
        } catch (Exception ex) {
            return new ModelAndView("error-page", Map.of(
                    "title", "Failed Account Activation",
                    "heading", "Failed !",
                    "message", ex.getMessage()
            ));
        }
    }
}