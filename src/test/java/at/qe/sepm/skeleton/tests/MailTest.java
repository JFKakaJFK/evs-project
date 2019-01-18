package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Mail;
import at.qe.sepm.skeleton.services.MailService;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail()
    {
        Mail mail = new Mail("christopher@kelter.at", "Test Subject", "Test Content");
        mailService.sendMail(mail);
    }
}
