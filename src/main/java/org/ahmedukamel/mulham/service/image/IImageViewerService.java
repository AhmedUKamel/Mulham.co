package org.ahmedukamel.mulham.service.image;

import java.io.IOException;

public interface IImageViewerService {
    Object getProfilePicture(String imageName) throws IOException;

    Object getLogo() throws IOException;
}