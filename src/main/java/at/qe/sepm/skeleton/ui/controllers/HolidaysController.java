package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.services.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
@Scope("request")
public class HolidaysController {

    @Autowired
    private HolidaysService holidaysService;

    private String name;
    private Date startDate;
    private Date endDate;

    public void addHolidays(){
        Holidays holidays = new Holidays();

        holidays.setName(name);
        holidays.setEndDate(endDate);
        holidays.setStartDate(startDate);

        holidaysService.saveHoliday(holidays);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
