package org.ahmedukamel.mulham.service.profile;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.constant.DirectoryConstants;
import org.ahmedukamel.mulham.dto.response.ProfileResponse;
import org.ahmedukamel.mulham.dto.request.UpdateProfileRequest;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.mapper.ProfileMapper;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.file.IFileService;
import org.ahmedukamel.mulham.updater.ProfileUpdater;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {
    final MessageSource messageSource;
    final UserRepository repository;
    final IFileService fileService;
    final ProfileMapper mapper;
    final ProfileUpdater updater;

    @Override
    public Object updateProfile(Object object) {
        UpdateProfileRequest request = (UpdateProfileRequest) object;
        User user = ContextHolderUtils.getUserOrElseThrow();
        updater.accept(user, request);
        repository.save(user);
        String message = messageSource.getMessage("successful.update.profile", null, Locale.ENGLISH);
        return new ApiResponse(true, message, null);
    }

    @Override
    public Object getProfile() {
        User user = ContextHolderUtils.getUserOrElseThrow();
        ProfileResponse data = mapper.apply(user);
        String message = messageSource.getMessage("successful.get.profile", null, Locale.ENGLISH);
        return new ApiResponse(true, message, Map.of("profile", data));
    }

    @Override
    public Object setProfilePicture(MultipartFile file) throws IOException {
        User user = ContextHolderUtils.getUserOrElseThrow();
        String imageName = user.getPicture();

        if (StringUtils.hasLength(imageName)) {
            fileService.delete(imageName, DirectoryConstants.PROFILE_IMAGES);
        }

        String name = user.getId() + System.currentTimeMillis() + "";
        imageName = fileService.save(file, name);
        user.setPicture(imageName);
        repository.save(user);

        String message = messageSource.getMessage("successful.save.profile.image", null, Locale.ENGLISH);
        return new ApiResponse(true, message, null);
    }

    @Override
    public Object removeProfilePicture() throws IOException {
        User user = ContextHolderUtils.getUserOrElseThrow();
        String imageName = user.getPicture();

        if (StringUtils.hasLength(imageName)) {
            fileService.delete(imageName, DirectoryConstants.PROFILE_IMAGES);
            user.setPicture(null);
            repository.save(user);
        }

        String message = messageSource.getMessage("successful.remove.profile.image", null, Locale.ENGLISH);
        return new ApiResponse(true, message, null);
    }
}