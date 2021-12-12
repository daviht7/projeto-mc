package com.daviholanda.cursomc.config;

import com.daviholanda.cursomc.service.DBService;
import com.daviholanda.cursomc.service.EmailService;
import com.daviholanda.cursomc.service.MockEmailService;
import com.daviholanda.cursomc.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.Email;
import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {

        if(!strategy.equals("create")) {
            return false;
        }

        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService smtpEmailService() {
        return new SmtpEmailService();
    }

}
