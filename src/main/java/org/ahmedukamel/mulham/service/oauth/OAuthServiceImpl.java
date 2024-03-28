package org.ahmedukamel.mulham.service.oauth;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.constant.OAuth2Constants;
import org.ahmedukamel.mulham.mapper.OAuth2Mapper;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseChecker;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    final UserRepository repository;
    final OAuth2Mapper mapper;

    @Override
    public Optional<User> authenticate(OAuth2AuthenticationToken authentication) {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User oAuth2User) {
            String registrationId = authentication.getAuthorizedClientRegistrationId();
            Provider provider = EnumUtils.findEnumInsensitiveCase(Provider.class, registrationId);

            Function<Provider, String> function = (p) -> p.equals(Provider.GOOGLE) ? StandardClaimNames.SUB :
                    p.equals(Provider.FACEBOOK) ? OAuth2Constants.FACEBOOK_ID : "";

            String providerId = oAuth2User.getAttribute(function.apply(provider));
            Optional<User> userOptional = repository.findByProviderIdAndProvider(providerId, provider);

            userOptional.ifPresent((user) -> DatabaseChecker.nonLocked(user::isAccountNonLocked));
            return Optional.of(userOptional.orElseGet(() -> repository.save(mapper.apply(oAuth2User, provider))));
        }
        return Optional.empty();
    }
}