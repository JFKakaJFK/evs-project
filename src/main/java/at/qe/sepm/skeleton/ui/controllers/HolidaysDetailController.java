package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.services.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("view")
public class HolidaysDetailController {

    @Autowired
    private HolidaysService holidaysService;

    private Holidays holidays;

    public void doReloadDate(){
        holidaysService.loadHoliday(holidays.getName());
    }

    public void doDeleteDate(){
        holidaysService.deleteHoliday(holidays);
        holidays = null;
    }

    public void doSaveDate(){
        holidays = holidaysService.saveHoliday(holidays);
    }

    public Holidays getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }
}
