package org.ahmedukamel.mulham.updater;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.ahmedukamel.mulham.dto.profile.UpdateProfileRequest;
import org.ahmedukamel.mulham.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class ProfileUpdater implements BiConsumer<User, UpdateProfileRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ProfileUpdater.class);

    @Override
    public void accept(User user, UpdateProfileRequest request) {
        BeanUtils.copyProperties(request, user);

        try {
            PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(request.phoneNumber(), "SA");
            user.setCountryCode(phoneNumber.getCountryCode());
            user.setNationalNumber(phoneNumber.getNationalNumber());
        } catch (NumberParseException exception) {
            logger.error("Phone Number Parse Failed: %s".formatted(exception.getMessage()));
        }

        if (request.theGender() != null) {
            user.setGender(request.theGender());
        }
    }
}