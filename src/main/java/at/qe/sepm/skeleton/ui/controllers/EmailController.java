package at.qe.sepm.skeleton.ui.controllers;
import at.qe.sepm.skeleton.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
/**
 * 
 * @author markus
 * Controller for booking tools via email
 */

@RestController //Using "@RestController" instead of simple "@Component" annotation 
@Scope("notify")

public class EmailController {
	
	@Autowired
	User user;
	
	@RequestMapping(value = "/sendemail")
	   public String sendEmail() {
	      return "Email wurde erfolgreich gesendet";
	   }   
	

	

	
	
	private void sendmail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true"); 
		   props.put("mail.smtp.host", "smtp.uibk.ac.at"); //connects to host. In this case uibk. 
		   props.put("mail.smtp.port", "25"); //port to connect to, I believe the default value is 25
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(user.getEmail(), user.getPassword()); //WARNING: Could be wrong 
		      }
		   });
		   
		   
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("test.hilpold@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail())); //sets recipients
		   msg.setSubject("Buchung"); //
		   msg.setContent("Buchung war erfolgreich", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Buchung war erfolgreich", "text/html");

		   /**
		    * I put the code below in comments instead of deleting it, even though we don't need attachments
		    * But should we ever want to do something pretty, there is also the code for sending attachments, just so you know
		    */
		   
		   
		   /*Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg); */  
		}
	
	
}
