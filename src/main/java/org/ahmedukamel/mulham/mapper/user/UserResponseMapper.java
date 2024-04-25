package org.ahmedukamel.mulham.mapper.user;

import org.ahmedukamel.mulham.constant.ImageConstants;
import org.ahmedukamel.mulham.dto.user.UserResponse;
import org.ahmedukamel.mulham.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.function.Function;

@Component
public class UserResponseMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        boolean hasPhoneNumber = user.getCountryCode() != null && user.getNationalNumber() != null;
        String phoneNumber = hasPhoneNumber ? "+%d%d".formatted(user.getCountryCode(), user.getNationalNumber()) : "";

        boolean hasPicture = StringUtils.hasLength(user.getPicture());
        String picture = hasPicture ? ImageConstants.PROFILE_API.formatted(user.getPicture()) : "";

        boolean hasDateOfBirth = user.getDateOfBirth() != null;
        String dateOfBirth = hasDateOfBirth ? user.getDateOfBirth().toString() : "";

        Collection<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        response.setHasPicture(hasPicture);
        response.setPicture(picture);
        response.setHasPhone(hasPhoneNumber);
        response.setPhoneNumber(phoneNumber);
        response.setHasDateOfBirth(hasDateOfBirth);
        response.setDateOfBirth(dateOfBirth);
        response.setAuthorities(authorities);
        return response;
    }
}