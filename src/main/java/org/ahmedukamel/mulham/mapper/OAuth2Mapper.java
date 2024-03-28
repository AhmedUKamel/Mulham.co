package org.ahmedukamel.mulham.mapper;

import org.ahmedukamel.mulham.constant.OAuth2Constants;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class OAuth2Mapper implements BiFunction<DefaultOAuth2User, Provider, User> {
    private static final BiFunction<DefaultOAuth2User, String, Object>
            function = OAuth2AuthenticatedPrincipal::getAttribute;

    @Override
    public User apply(DefaultOAuth2User oAuth2User, Provider provider) {
        User user = switch (provider) {
            case GOOGLE -> mapGoogleUser(oAuth2User);
            case FACEBOOK -> mapFacebookUser(oAuth2User);
            default -> new User();
        };
        user.setEnabled(true);
        user.setProvider(provider);
        return user;
    }

    public static User mapGoogleUser(DefaultOAuth2User defaultOAuth2User) {
        User user = new User();
        user.setEmail(function.apply(defaultOAuth2User, StandardClaimNames.EMAIL) + "");
        user.setProviderId(function.apply(defaultOAuth2User, StandardClaimNames.SUB) + "");
        user.setFirstName(function.apply(defaultOAuth2User, StandardClaimNames.GIVEN_NAME) + "");
        user.setLastName(function.apply(defaultOAuth2User, StandardClaimNames.FAMILY_NAME) + "");
        return user;
    }

    public static User mapFacebookUser(DefaultOAuth2User defaultOAuth2User) {
        User user = new User();
        user.setEmail(function.apply(defaultOAuth2User, StandardClaimNames.EMAIL) + "");
        user.setProviderId(function.apply(defaultOAuth2User, OAuth2Constants.FACEBOOK_ID) + "");
        String[] names = (function.apply(defaultOAuth2User, StandardClaimNames.NAME) + "").split(" ");
        user.setFirstName(names[0]);
        user.setLastName(names.length > 1 ? names[1] : names[0]);
        return user;
    }
}