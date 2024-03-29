package org.ahmedukamel.mulham.service.image;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.constant.DirectoryConstants;
import org.ahmedukamel.mulham.service.file.IFileService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageViewerService implements IImageViewerService {
    final IFileService fileService;

    @Override
    public Object getProfilePicture(String imageName) throws IOException {
        byte[] image = fileService.get(imageName, DirectoryConstants.PROFILE_IMAGES);

        if (image == null) {
            Resource resource = new ClassPathResource("static/images/no-profile-picture.png");
            image = resource.getContentAsByteArray();
        }

        return image;
    }

    @Override
    public Object getImage(String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/images/" + imageName);

        if (!resource.isReadable()) {
            resource = new ClassPathResource("static/images/image-not-found.png");
        }

        return resource.getContentAsByteArray();
    }
}