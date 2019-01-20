package at.qe.sepm.skeleton.ui.beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.configs.EmailConfig;
import at.qe.sepm.skeleton.services.MailService;


@Component
@Scope("request")

public class EmailBean { //This Bean class doesn't send anything either
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private EmailConfig mailServiceConfig;

	
	public void sendTestMail(String to) {
		String from = "admin1";
		String subject = "Mail test";
		String text = "This is a test message!";
				
		try {
			mailService.sendEmail(from, to, subject, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
