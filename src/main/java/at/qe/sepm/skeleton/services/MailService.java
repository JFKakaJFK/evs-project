package at.qe.sepm.skeleton.services;

import java.util.Properties;

import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import at.qe.sepm.skeleton.model.Email;
import freemarker.template.Configuration;
import freemarker.template.Template;
 
@Service
public class MailService {
	@Autowired
    private JavaMailSender sender;
 
    
	/**
	 * 
	 * @param from: from where it is sent
	 * @param to: to whom it is sent
	 * @param subject: pretty obvious
	 * @param content: pretty obvious
	 * @throws Exception: Exception 
	 */
    
    public void sendEmail(String from, String to, String subject, String content) throws Exception {
    	SimpleMailMessage message = new SimpleMailMessage(); 
    	message.setFrom("test.hilpold@gmail.com");
        message.setTo("test.hilpold@gmail.com"); 
        message.setSubject("bla"); 
        message.setText("bla");
        try {
        	sender.send(message);
        } catch (MailException e) {
        	System.out.println(e.getMessage());
        }
        
        
    }
    
    @Bean //Should send test Email
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
          "This is the test email template for your email:\n%s\n");
        return message;
    }
}
