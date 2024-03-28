package org.ahmedukamel.mulham.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AccountActivationMailSender implements IMailSenderService {
    final TemplateEngine templateEngine;
    final JavaMailSender mailSender;

    @Value(value = "spring.mail.username")
    private String from;

    @Override
    public void send(Object object) {
        try {
            Token token = (Token) object;

            Context context = new Context();
            context.setVariable("name", token.getUser().getFirstName());
            context.setVariable("link", getActivationLink(token));
            context.setVariable("expiration", getExpirationDate(token));
            String content = templateEngine.process("account-activation-mail", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(token.getUser().getEmail());
            messageHelper.setSubject("Welcome to Mulham! Verify Your Email Address");
            messageHelper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException ignored) {
        }
    }

    private static String getExpirationDate(Token token) {
        return token.getExpiration().format(DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm"));
    }

    private static String getActivationLink(Token token) {
        return String.format("%s?token=%s&email=%s", "https://api.mulham.co/p/activate", token.getId(), token.getUser().getEmail());
    }
}