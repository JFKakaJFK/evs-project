package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.services.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Date;

@Component
@Scope("request")
public class AddHolidaysBean {

    @Autowired
    private HolidaysService holidaysService;

    private String name;
    private Date startDate;
    private Date endDate;

    public void addHolidays(){
        if(startDate.before(endDate) && startDate.after(new Date())){
            Holidays holidays = new Holidays();

            holidays.setName(name);
            holidays.setEndDate(endDate);
            holidays.setStartDate(startDate);

            holidaysService.saveHoliday(holidays);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Success", "Feiertag erfolgreich angepasst.")
            );
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Der eingegebe Feiertag ist ungülitg.")
            );
        }
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
