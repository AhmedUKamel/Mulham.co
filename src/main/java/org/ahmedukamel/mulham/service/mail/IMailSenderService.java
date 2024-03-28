package org.ahmedukamel.mulham.service.mail;

import jakarta.mail.MessagingException;

public interface IMailSenderService {
    void send(Object object) throws MessagingException;
}