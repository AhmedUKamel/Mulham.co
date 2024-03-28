package org.ahmedukamel.mulham.mapper;

import org.ahmedukamel.mulham.dto.response.ProfileResponse;
import org.ahmedukamel.mulham.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.function.Function;

@Component
public class ProfileMapper implements Function<User, ProfileResponse> {
    @Override
    public ProfileResponse apply(User user) {
        return new ProfileResponse(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                getPhoneNumber(user),
                getDateOfBirth(user),
                user.getGender().getName(),
                getPicture(user),
                StringUtils.hasLength(user.getPicture()),
                user.getProvider().name(),
                user.getRole().name(),
                getPermissions(user));
    }

    private String getPhoneNumber(User user) {
        if (user.getCountryCode() != null && user.getNationalNumber() != null) {
            return "+" + user.getCountryCode() + user.getNationalNumber();
        }
        return "";
    }

    private Collection<String> getPermissions(User user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    private String getDateOfBirth(User user) {
        return user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "";
    }

    private String getPicture(User user) {
        return user.getPicture() != null ? "http://localhost:8080/api/v1/p/image/" + user.getPicture() : "";
    }
}