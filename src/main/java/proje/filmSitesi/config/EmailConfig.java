package proje.filmSitesi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Bean
    public String fromEmail() {
        return fromEmail;
    }
}