package org.ahmedukamel.mulham.service.profile;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.constant.DirectoryConstants;
import org.ahmedukamel.mulham.dto.profile.UpdateProfileRequest;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.profile.ProfileResponse;
import org.ahmedukamel.mulham.mapper.profile.ProfileResponseMapper;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.file.IFileService;
import org.ahmedukamel.mulham.updater.ProfileUpdater;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {
    final UserRepository repository;
    final IFileService service;
    final ProfileResponseMapper mapper;
    final ProfileUpdater updater;

    @Override
    public Object updateProfile(Object object) {
        UpdateProfileRequest request = (UpdateProfileRequest) object;
        User user = ContextHolderUtils.getUserOrElseThrow();

        updater.accept(user, request);

        User savedUser = repository.save(user);
        ProfileResponse response = mapper.apply(savedUser);

        return new ApiResponse(true, "User profile have been updated successfully.", response);
    }

    @Override
    public Object getProfile() {
        User user = ContextHolderUtils.getUserOrElseThrow();
        ProfileResponse response = mapper.apply(user);

        return new ApiResponse(true, "User profile have been fetched successfully.", response);
    }

    @Override
    public Object setProfilePicture(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Missing profile picture.");
        }

        User user = ContextHolderUtils.getUserOrElseThrow();
        String imageName = user.getPicture();

        if (StringUtils.hasLength(imageName)) {
            service.delete(imageName, DirectoryConstants.PROFILE_IMAGES);
        }

        imageName = user.getId() + System.currentTimeMillis() + "";
        imageName = service.save(file, imageName, DirectoryConstants.PROFILE_IMAGES);
        user.setPicture(imageName);
        repository.save(user);

        return new ApiResponse(true, "User profile image have been saved successfully.", "");
    }

    @Override
    public void removeProfilePicture() throws IOException {
        User user = ContextHolderUtils.getUserOrElseThrow();
        String imageName = user.getPicture();

        if (StringUtils.hasLength(imageName)) {
            service.delete(imageName, DirectoryConstants.PROFILE_IMAGES);
            user.setPicture(null);
            repository.save(user);
            return;
        }

        throw new RuntimeException("No profile picture found.");
    }
}