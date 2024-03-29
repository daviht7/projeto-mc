package com.daviholanda.cursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {

        logger.info("Simulando envio de email...");
        logger.info(msg.toString());
        mailSender.send(msg);
        logger.info("Email enviado.");

    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {

        logger.info("Simulando envio de email em html...");
        logger.info(msg.toString());
        javaMailSender.send(msg);
        logger.info("Email enviado.");

    }

}
