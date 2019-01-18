package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.EmailProperties;
import at.qe.sepm.skeleton.model.Mail;
import org.hibernate.validator.constraints.Email;
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
    private EmailProperties emailProperties;

	@Autowired
    private JavaMailSender sender;

    JavaMailSenderImpl javaMailSender;

    @PostConstruct
    public void Init()
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

    public void sendMail(Mail mail)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getEmail());
        message.setFrom(emailProperties.getEmail());

        javaMailSender.send(message);
    }

    @Deprecated
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

    @Deprecated
    @Bean //Should send test Email
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
          "This is the test email template for your email:\n%s\n");
        return message;
    }
}
