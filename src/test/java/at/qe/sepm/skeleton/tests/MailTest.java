package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Mail;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.MailService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SchedulerBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MailTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulerBean schedulerBean;

    @Test
    public void testSendMail()
    {
        Mail mail = new Mail("christopher@kelter.at", "Test Subject", "<b>Test Content</b>");
        mailService.sendMail(mail);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSendMailOverdue()
    {
        User user = userService.loadUser("admin");
        user.setEmail("christopher@kelter.at");
        schedulerBean.sendEmailOverdue(user);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSendMailLongBeforeOverdue()
    {
        User user = userService.loadUser("admin");
        user.setEmail("christopher@kelter.at");
        schedulerBean.sendEmailBeforeOverdue(user);
    }
}
