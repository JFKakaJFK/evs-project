package at.qe.sepm.skeleton.services;

import java.util.Properties;

import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import at.qe.sepm.skeleton.model.Email;
import freemarker.template.Configuration;
import freemarker.template.Template;
 
@Service
public class EmailService {
	@Autowired
    private JavaMailSender sender;
 
    @Autowired
    private Configuration freemarkerConfig;
 
    
    public void sendEmail(Email mail) throws Exception {
        MimeMessage message = sender.createMimeMessage();
 
        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        Template t = freemarkerConfig.getTemplate("email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
 
        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());
 
        sender.send(message);
    }
}
