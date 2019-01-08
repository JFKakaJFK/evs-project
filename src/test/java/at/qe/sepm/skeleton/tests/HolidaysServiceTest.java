package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.HolidaysRepository;
import at.qe.sepm.skeleton.services.HolidaysService;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.HOLDING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HolidaysServiceTest {
    @Autowired
    private HolidaysService holidaysService;

    @Autowired
    private UserService userService;

    @Test
    public void testDatainitialization()
    {
        Assert.assertEquals("Insufficient amount of days for holidays initialized for test data source", 1, holidaysService.getAllHolidays().size());
        for (Holidays holiday : holidaysService.getAllHolidays()) {
            if ("Test".equals(holiday.getName())) {
                Assert.assertTrue("Holiday \"test\" does not have Startdate", holiday.getStartDate().toString().contains("2016-01-01 00:00:00"));
                Assert.assertTrue("Holiday \"test\" does not have Enddate", holiday.getEndDate().toString().contains("2016-01-01 00:00:00"));
                Assert.assertNotNull("Holiday \"test\"does not have a createUser defined", holiday.getCreateUser());
                Assert.assertNotNull("Holiday \"test\" does not have a createDate defined", holiday.getCreateDate());
                Assert.assertNull("Holiday \"test\" has a updateUser defined", holiday.getUpdateUser());
                Assert.assertNull("Holiday \"test\" has a updateDate defined", holiday.getUpdateDate());
            }

            else {
                Assert.fail("Unknown holidayday \"" + holiday.getName() + "\" loaded from test data source via HolidaysService.getAllHolidays");
            }
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteHoliday() {
        Holidays toBeDeletedDay = holidaysService.loadHoliday("Test");
        Assert.assertNotNull("Day could not be loaded from test data source", toBeDeletedDay);

        holidaysService.deleteHoliday(toBeDeletedDay);

        Assert.assertEquals("No day has been deleted after calling HolidaysSErvice.deleteHoliday", 0, holidaysService.getAllHolidays().size());
        Holidays deletedDay = holidaysService.loadHoliday("Test");
        Assert.assertNull("Deleted Holiday TEst could still be loaded from test data source via HolidaysService.loadHoliday", deletedDay);

        for (Holidays remainingDay : holidaysService.getAllHolidays()) {
            Assert.assertNotEquals("Deleted Holiday Test could still be loaded from test data source via HolidaysService.getAllHolidays", toBeDeletedDay.getName(), remainingDay.getName());
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateHoliday() throws ParseException {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);

        Holidays toBeSavedDay = holidaysService.loadHoliday("Test");
        Assert.assertNotNull("Holiday Test could not be loaded from test data source", toBeSavedDay);

        Assert.assertNull("Holiday \"Test\" has a updateUser defined", toBeSavedDay.getUpdateUser());
        Assert.assertNull("Holiday \"Test\" has a updateDate defined", toBeSavedDay.getUpdateDate());


        toBeSavedDay.setEndDate(createDate(2017,1,0));
        holidaysService.saveHoliday(toBeSavedDay);

        Holidays freshlyLoadedDay = holidaysService.loadHoliday("Test");
        Assert.assertNotNull("Holiday Test could not be loaded from test data source after being saved", freshlyLoadedDay);
        Assert.assertNotNull("Holiday \"Test\" does not have a updateUser defined after being saved", freshlyLoadedDay.getUpdateUser());
        Assert.assertEquals("Holiday \"Test\" has wrong updateUser set", adminUser, freshlyLoadedDay.getUpdateUser());
        Assert.assertNotNull("Holiday \"Test\" does not have a updateDate defined after being saved", freshlyLoadedDay.getUpdateDate());
        Assert.assertEquals("Holiday \"Test\" does not have a the correct Enddate attribute stored being saved", "2017-01-01 00:00:00.0", freshlyLoadedDay.getEndDate().toString());
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateHoliday() throws ParseException {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);

        Holidays toBeCreatedDay = new Holidays();
        toBeCreatedDay.setName("Test 2");
        toBeCreatedDay.setStartDate(createDate(2018,1,1));
        toBeCreatedDay.setEndDate(createDate(2018,1,1));
        holidaysService.saveHoliday(toBeCreatedDay);

        Holidays freshlyCreadedDay = holidaysService.loadHoliday("Test 2");
        Assert.assertNotNull("New holiday could not be loaded from test data source after being saved", freshlyCreadedDay);
        Assert.assertEquals("Holiday \"Test 2\" does not have a the correct day_name attribute stored being saved", "Test 2", freshlyCreadedDay.getName());
        Assert.assertEquals("Holiday \"Test 2\" does not have a the correct Startdate attribute stored being saved", "2018-01-01 00:00:00.0", freshlyCreadedDay.getStartDate().toString());
        Assert.assertEquals("Holiday \"Test 2\" does not have a the correct Enddate attribute stored being saved", "2018-01-01 00:00:00.0", freshlyCreadedDay.getEndDate().toString());
        Assert.assertNotNull("Holiday \"Test 2\" does not have a createUser defined after being saved", freshlyCreadedDay.getCreateUser());
        Assert.assertEquals("Holiday \"Test 2\" has wrong createUser set", adminUser, freshlyCreadedDay.getCreateUser());
        Assert.assertNotNull("Holiday \"Test 2\" does not have a createDate defined after being saved", freshlyCreadedDay.getCreateDate());
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedSaveHolidays() {
        Holidays day = holidaysService.loadHoliday("Test");
        Assert.assertEquals("Call to holidaysService.loadHoliday returned wrong day", "Test", day.getName());
        holidaysService.saveHoliday(day);
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"EMPLOYEE"})
    public void testUnauthorizedDeleteHolidays() {
        Holidays day = holidaysService.loadHoliday("Test");
        Assert.assertEquals("Call to holidaysService.loadHoliday returned wrong day", "Test", day.getName());
        holidaysService.deleteHoliday(day);
    }

    public Date createDate(int Y, int M, int d) throws ParseException {
        String input = Integer.toString(Y)+"-"+Integer.toString(M)+"-"+Integer.toString(d);
        SimpleDateFormat format = new SimpleDateFormat("Y-M-d");
        return new Date(format.parse(input).getTime());
    }
}
