package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.EmailProperties;
import at.qe.sepm.skeleton.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Scope("application")
public class MailService {

    @Autowired
    private EmailProperties emailProperties;

	@Autowired
    private JavaMailSender sender;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MailService.class);

    private JavaMailSenderImpl javaMailSender;

    @PostConstruct
    public void Init()
    {
        System.setProperty("mail.mime.charset", "UTF-8");
        this.javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setUsername(emailProperties.getUsername());
        javaMailSender.setPassword(emailProperties.getPassword());
        javaMailSender.setPort(emailProperties.getPort());
        javaMailSender.setProtocol(emailProperties.getProtocol());
        javaMailSender.setDefaultEncoding("UTF-8");

        // create java mail properties
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", emailProperties.isMailAuth());
        mailProperties.put("mail.smtp.starttls.enable", emailProperties.isMailTls());

        // set java mail properties
        javaMailSender.setJavaMailProperties(mailProperties);
    }

    public void sendMail(Mail mail)
    {
        try
        {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(mail.getContent(), "text/html; charset=UTF-8");
            helper.setTo(mail.getEmail());
            helper.setSubject(mail.getSubject());
            helper.setFrom(emailProperties.getEmail());

            javaMailSender.send(mimeMessage);
        }

        catch (Exception e)
        {
            logger.error("FAILED to send mail: " + e.getMessage());
        }

    }

}
