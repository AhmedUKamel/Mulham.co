package org.ahmedukamel.mulham.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class AccountActivationMailSender implements IMailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(AccountActivationMailSender.class);
    final TemplateEngine templateEngine;
    final JavaMailSender mailSender;

    @Value(value = "spring.mail.username")
    private String from;

    public AccountActivationMailSender(TemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    @Override
    public void send(Email email) {
        try {
            Context context = new Context();
            email.getVariables().forEach(context::setVariable);
            String text = templateEngine.process("account-activation-mail", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(email.getReceiver());
            messageHelper.setSubject("Welcome to Mulham! Verify Your Email Address");
            messageHelper.setText(text, true);

            mailSender.send(message);
        } catch (MessagingException exception) {
            logger.error("Sending Account Activation Email Failed: %s".formatted(exception.getMessage()));
        }
    }
}