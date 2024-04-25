package org.ahmedukamel.mulham.service.profile;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProfileService {
    Object updateProfile(Object object);

    Object getProfile();

    Object setProfilePicture(MultipartFile file) throws IOException;

    void removeProfilePicture() throws IOException;
}