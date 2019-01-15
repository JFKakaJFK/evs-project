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

    // TODO maybe: holidays view edit dialog does sometimes not open(not rendered when holidays is empty)?
    /**
     * Attribute to cache the currently displayed openingHours
     */
    private Holidays holidays;

    /**
     * Sets the currently displayed holiday and reloads it form db. This holiday is
     * targeted by any further calls of
     * {@link #doReloadDate()}, {@link #doSaveDate()} and
     * {@link #doDeleteDate()}.
     *
     * @param holidays
     */
    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
        doReloadDate();
    }

    /**
     * Returns the currently displayed holiday.
     *
     * @return
     */
    public Holidays getHolidays() {
        return holidays;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadDate() {
        holidays = holidaysService.loadHoliday(holidays.getName());
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveDate() {
        holidays = this.holidaysService.saveHoliday(holidays);
    }

    /**
     * Action to delete the currently displayed user.
     */
    public void doDeleteDate() {
        this.holidaysService.deleteHoliday(holidays);
        holidays = null;
    }
}
