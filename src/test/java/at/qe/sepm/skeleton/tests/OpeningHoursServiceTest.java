package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import nl.jqno.equalsverifier.internal.lib.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Calendar;

import static java.time.LocalTime.parse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class OpeningHoursServiceTest {
   @Autowired
   private OpeningHoursService openingHoursService;

    @Autowired
    private UserService userService;

    @Test
    public void testDatainitialization()
    {
        Assert.assertEquals("Insufficient amount of days for opening hours initialized for test data source", 5, openingHoursService.getAllOpeningHours().size());
        for (OpeningHours openingHour : openingHoursService.getAllOpeningHours()) {
            if ("Montag".equals(openingHour.getDay())) {
                Assert.assertTrue("Day \"Montag\" does not have Starttime", openingHour.getStartTime().toString().contains("08:00:00"));
                Assert.assertTrue("Day \"Montag\" does not have Endtime", openingHour.getEndTime().toString().contains("17:00:00"));
                Assert.assertTrue("Day \"Montag\" does not have Startpause", openingHour.getStartPause().toString().contains("12:00:00"));
                Assert.assertTrue("Day \"Montag\" does not have Endpuase", openingHour.getEndPause().toString().contains("14:00:00"));
                Assert.assertNotNull("Day \"Montag\" does not have a createUser defined", openingHour.getCreateUser());
                Assert.assertNotNull("Day \"Montag\" does not have a createDate defined", openingHour.getCreateDate());
                Assert.assertNull("Day \"Montag\" has a updateUser defined", openingHour.getUpdateUser());
                Assert.assertNull("Day \"Montag\" has a updateDate defined", openingHour.getUpdateDate());
            } else if ("Dienstag".equals(openingHour.getDay())) {
                Assert.assertTrue("Day \"Dienstag\" does not have Starttime", openingHour.getStartTime().toString().contains("08:00:00"));
                Assert.assertTrue("Day \"Dienstag\" does not have Endtime", openingHour.getEndTime().toString().contains("17:00:00"));
                Assert.assertTrue("Day \"Dienstag\" does not have Startpause", openingHour.getStartPause().toString().contains("12:00:00"));
                Assert.assertTrue("Day \"Dienstag\" does not have Endpuase", openingHour.getEndPause().toString().contains("14:00:00"));
                Assert.assertNotNull("Day \"Dienstag\" does not have a createUser defined", openingHour.getCreateUser());
                Assert.assertNotNull("Day \"Dienstag\" does not have a createDate defined", openingHour.getCreateDate());
                Assert.assertNull("Day \"Dienstag\" has a updateUser defined", openingHour.getUpdateUser());
                Assert.assertNull("Day \"Dienstag\" has a updateDate defined", openingHour.getUpdateDate());
            } else if ("Mittwoch".equals(openingHour.getDay())) {
                Assert.assertTrue("Day \"Mittwoch\" does not have Starttime", openingHour.getStartTime().toString().contains("08:00:00"));
                Assert.assertTrue("Day \"Mittwoch\" does not have Endtime", openingHour.getEndTime().toString().contains("17:00:00"));
                Assert.assertTrue("Day \"Mittwoch\" does not have Startpause", openingHour.getStartPause().toString().contains("12:00:00"));
                Assert.assertTrue("Day \"Mittwoch\" does not have Endpuase", openingHour.getEndPause().toString().contains("14:00:00"));
                Assert.assertNotNull("Day \"Mittwoch\" does not have a createUser defined", openingHour.getCreateUser());
                Assert.assertNotNull("Day \"Mittwoch\" does not have a createDate defined", openingHour.getCreateDate());
                Assert.assertNull("Day \"Mittwoch\" has a updateUser defined", openingHour.getUpdateUser());
                Assert.assertNull("Day \"Mittwoch\" has a updateDate defined", openingHour.getUpdateDate());
            }  else if ("Donnerstag".equals(openingHour.getDay())) {
                Assert.assertTrue("Day \"Donnerstag\" does not have Starttime", openingHour.getStartTime().toString().contains("08:00:00"));
                Assert.assertTrue("Day \"Donnerstag\" does not have Endtime", openingHour.getEndTime().toString().contains("17:00:00"));
                Assert.assertTrue("Day \"Donnerstag\" does not have Startpause", openingHour.getStartPause().toString().contains("12:00:00"));
                Assert.assertTrue("Day \"Donnerstag\" does not have Endpuase", openingHour.getEndPause().toString().contains("14:00:00"));
                Assert.assertNotNull("Day \"Donnerstag\" does not have a createUser defined", openingHour.getCreateUser());
                Assert.assertNotNull("Day \"Donnerstag\" does not have a createDate defined", openingHour.getCreateDate());
                Assert.assertNull("Day \"Donnerstag\" has a updateUser defined", openingHour.getUpdateUser());
                Assert.assertNull("Day \"Donnerstag\" has a updateDate defined", openingHour.getUpdateDate());
            } else if ("Freitag".equals(openingHour.getDay())) {
                Assert.assertTrue("Day \"Freitag\" does not have Starttime", openingHour.getStartTime().toString().contains("08:00:00"));
                Assert.assertTrue("Day \"Freitag\" does not have Endtime", openingHour.getEndTime().toString().contains("12:00:00"));
                Assert.assertNull("Day \"Freitag\" does not have Startpause", openingHour.getStartPause());
                Assert.assertNull("Day \"Freitag\" does not have Endpuase", openingHour.getEndPause());
                Assert.assertNotNull("Day \"Freitag\" does not have a createUser defined", openingHour.getCreateUser());
                Assert.assertNotNull("Day \"Freitag\" does not have a createDate defined", openingHour.getCreateDate());
                Assert.assertNull("Day \"Freitag\" has a updateUser defined", openingHour.getUpdateUser());
                Assert.assertNull("Day \"Freitag\" has a updateDate defined", openingHour.getUpdateDate());
            }

            else {
                Assert.fail("Unknown day \"" + openingHour.getDay() + "\" loaded from test data source via OpeningHourService.getAllOpeningHours");
            }
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteOpeningHourDay() {
        OpeningHours toBeDeletedDay = openingHoursService.loadOpeningHour("Montag");
        Assert.assertNotNull("Day could not be loaded from test data source", toBeDeletedDay);

        openingHoursService.deleteOpeningHour(toBeDeletedDay);

        Assert.assertEquals("No day has been deleted after calling OpeningHoursService.deleteDay", 4, openingHoursService.getAllOpeningHours().size());
        OpeningHours deletedDay = openingHoursService.loadOpeningHour("Montag");
        Assert.assertNull("Deleted Day Montag could still be loaded from test data source via OpeningHoursService.loadDay", deletedDay);

        for (OpeningHours remainingDay : openingHoursService.getAllOpeningHours()) {
            Assert.assertNotEquals("Deleted Day Montag could still be loaded from test data source via OpeningHoursService.getAllOpeningHours", toBeDeletedDay.getDay(), remainingDay.getDay());
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateOpeningHourDay() throws ParseException {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);

        OpeningHours toBeSavedDay = openingHoursService.loadOpeningHour("Freitag");
        Assert.assertNotNull("Day Freitag could not be loaded from test data source", toBeSavedDay);

        Assert.assertNull("Day \"Freitag\" has a updateUser defined", toBeSavedDay.getUpdateUser());
        Assert.assertNull("Day \"Freitag\" has a updateDate defined", toBeSavedDay.getUpdateDate());

        //toBeSavedDay.setEndTime(Date.from(Instant.from(LocalTime.parse("13:00:00"))));


        toBeSavedDay.setEndTime(createDate(13,0,0));
        openingHoursService.saveOpeningHour(toBeSavedDay);

        OpeningHours freshlyLoadedDay = openingHoursService.loadOpeningHour("Freitag");
        Assert.assertNotNull("Day Freitag could not be loaded from test data source after being saved", freshlyLoadedDay);
        Assert.assertNotNull("Day \"Freitag\" does not have a updateUser defined after being saved", freshlyLoadedDay.getUpdateUser());
        Assert.assertEquals("Day \"Freitag\" has wrong updateUser set", adminUser, freshlyLoadedDay.getUpdateUser());
        Assert.assertNotNull("Day \"Freitag\" does not have a updateDate defined after being saved", freshlyLoadedDay.getUpdateDate());
        Assert.assertEquals("Day \"Freitag\" does not have a the correct Endtime attribute stored being saved", "13:00:00", freshlyLoadedDay.getEndTime().toString());
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateOpeningHourDay() throws ParseException {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);

        OpeningHours toBeCreatedDay = new OpeningHours();
        toBeCreatedDay.setDay("Samstag");
        toBeCreatedDay.setStartTime(createDate(9,0,0));
        toBeCreatedDay.setEndTime(createDate(12,0,0));
        openingHoursService.saveOpeningHour(toBeCreatedDay);

        OpeningHours freshlyCreadedDay = openingHoursService.loadOpeningHour("Samstag");
        Assert.assertNotNull("New day could not be loaded from test data source after being saved", freshlyCreadedDay);
        Assert.assertEquals("Day \"Samstag\" does not have a the correct day_name attribute stored being saved", "Samstag", freshlyCreadedDay.getDay());
        Assert.assertEquals("Day \"Samstag\" does not have a the correct Starttime attribute stored being saved", "09:00:00", freshlyCreadedDay.getStartTime().toString());
        Assert.assertEquals("Day \"Samstag\" does not have a the correct Endtime attribute stored being saved", "12:00:00", freshlyCreadedDay.getEndTime().toString());
        Assert.assertNull("Day \"Samstag\" does not have a the correct Startpause attribute stored being saved", freshlyCreadedDay.getStartPause());
        Assert.assertNull("Day \"Samstag\" does not have a the correct Endpause attribute stored being saved", freshlyCreadedDay.getEndPause());
        Assert.assertNotNull("Day \"Samstag\" does not have a createUser defined after being saved", freshlyCreadedDay.getCreateUser());
        Assert.assertEquals("Day \"Samstag\" has wrong createUser set", adminUser, freshlyCreadedDay.getCreateUser());
        Assert.assertNotNull("Day \"Samstag\" does not have a createDate defined after being saved", freshlyCreadedDay.getCreateDate());
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedSaveOpeningHoursDay() {
        OpeningHours day = openingHoursService.loadOpeningHour("Freitag");
        Assert.assertEquals("Call to openingHoursService.loadOpeningHour returned wrong user", "Freitag", day.getDay());
        openingHoursService.saveOpeningHour(day);
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedDeleteOpeningHoursDay() {
        OpeningHours day = openingHoursService.loadOpeningHour("Mittwoch");
        Assert.assertEquals("Call to openingHoursService.loadOpeningHour returned wrong day", "Mittwoch", day.getDay());
        openingHoursService.deleteOpeningHour(day);
    }

    public Date createDate(int h, int m, int s) throws ParseException {
        String input = Integer.toString(h)+":"+Integer.toString(m)+":"+Integer.toString(s);
        SimpleDateFormat format = new SimpleDateFormat("H:m:s");
        return new Date(format.parse(input).getTime());
    }

}
