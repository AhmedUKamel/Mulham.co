package org.ahmedukamel.mulham.updater;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.ahmedukamel.mulham.dto.request.UpdateProfileRequest;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.util.LocalDateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class ProfileUpdater implements BiConsumer<User, UpdateProfileRequest> {
    @Override
    public void accept(User user, UpdateProfileRequest request) {
        BeanUtils.copyProperties(request, user);

        try {
            PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(request.phoneNumber(), "SA");
            user.setCountryCode(phoneNumber.getCountryCode());
            user.setNationalNumber(phoneNumber.getNationalNumber());
        } catch (NumberParseException ignore) {
        }

        try {
            user.setDateOfBirth(LocalDateUtils.getDate(request.dateOfBirth()));
        } catch (Exception ignore) {
        }

        if (request.theGander() != null) {
            user.setGender(request.theGander());
        }
    }
}
