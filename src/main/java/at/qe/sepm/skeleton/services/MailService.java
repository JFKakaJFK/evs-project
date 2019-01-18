package at.qe.sepm.skeleton.services;

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

@Component
@Service
public class MailService {

	@Autowired
    private JavaMailSender sender;

    JavaMailSenderImpl javaMailSender;

	@PostConstruct
    public void Init()
    {
        this.javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("evs2.uibk@gmail.com");
        javaMailSender.setPassword("evs2!uibk2019");
        //javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");

        // create java mail properties
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);

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
