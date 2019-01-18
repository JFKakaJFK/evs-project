package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.services.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Date;

@Controller
@Scope("view")
public class HolidaysDetailController {

    @Autowired
    private HolidaysService holidaysService;

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
     * Action to force a reload of the currently displayed holiday.
     */
    public void doReloadDate() {
        holidays = holidaysService.loadHoliday(holidays.getName());
    }

    /**
     * Action to save the currently displayed holiday.
     */
    public void doSaveDate() {
        if(holidays.getStartDate().before(holidays.getEndDate()) && holidays.getStartDate().after(new Date())){
            holidays = this.holidaysService.saveHoliday(holidays);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Success", "Feiertag erfolgreich angepasst.")
            );
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Der eingegebe Feiertag ist ungülitg.")
            );
        }
    }

    /**
     * Action to delete the currently displayed holiday.
     */
    public void doDeleteDate() {
        this.holidaysService.deleteHoliday(holidays);
        holidays = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Feiertag erfolgreich gelöscht.")
        );
    }
}
