package org.ahmedukamel.mulham.service.mail;

import java.io.File;

public interface IMailSenderService {
    default void send(Email email) {}

    default void send(Email email, File... files) {}
}