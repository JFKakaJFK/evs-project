package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class MailService {

	@Autowired
    private JavaMailSender sender;

    JavaMailSenderImpl javaMailSender;

    @Autowired
    public MailService(EmailProperties emailProperties)
    {
        this.javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setUsername(emailProperties.getUsername());
        javaMailSender.setPassword(emailProperties.getPassword());
        javaMailSender.setPort(emailProperties.getPort());
        javaMailSender.setProtocol(emailProperties.getProtocol());

        // create java mail properties
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", emailProperties.isMailAuth());
        mailProperties.put("mail.smtp.starttls.enable", emailProperties.isMailTls());

        // set java mail properties
        javaMailSender.setJavaMailProperties(mailProperties);
    }


	public void newMailSender(String email_to, String email_subject, String email_content)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(email_subject);
        message.setText(email_content);
        message.setTo(email_to);
        message.setFrom("evs2.uibk@gmail.com");

        javaMailSender.send(message);
    }

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
