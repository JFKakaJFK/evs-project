package at.qe.sepm.skeleton.configs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.services.UserService;

@Component
@Configuration
public class EmailConfig { //this is also a test method, feel free to carry out tests
	
	@Autowired
	UserService userService;
	
	private String host = "smtp.gmail.com";
	private String port = "587";
	private String username = "test.hilpold@gmail.com";
	private String password = "gavosa03";
		
	
	@Bean //Bean sends exception
    public JavaMailSender getEmailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
 
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }    
}
