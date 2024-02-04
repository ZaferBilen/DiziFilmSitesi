package proje.filmSitesi.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailDaoImpl implements EmailDao{
	
		private JavaMailSender mailSender;
		
		@Value("${spring.mail.username}")
		private String fromEmail;

		@Override
		public void dogrulamaKoduGonder(String toEmail, String dogrulamaKodu) {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);

	        try {
	            helper.setFrom(fromEmail);
	            helper.setTo(toEmail);
	            helper.setSubject("Doğrulama Kodu");
	            helper.setText("Doğrulama Kodunuz: " + dogrulamaKodu);

	            mailSender.send(message);
	        } catch (MessagingException e) {        
	            e.printStackTrace();
	        }
	    }
	}