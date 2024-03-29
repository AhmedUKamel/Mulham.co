package org.ahmedukamel.mulham.service.image;

import java.io.IOException;

public interface IImageViewerService {
    Object getProfilePicture(String imageName) throws IOException;

    Object getImage(String imageName) throws IOException;
}