package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Component
@Scope("view")
public class OpeningHoursDetailController {

    @Autowired
    private OpeningHoursService openingHoursService;

    /**
     * Attribute to cache the currently displayed openingHours
     */
    private OpeningHours openingHours;

    /**
     * Sets the currently displayed openingHours and reloads it form db. This openingHours is
     * targeted by any further calls of
     * {@link #doReloadHours()}, {@link #doSaveHours()} and
     *
     * @param openingHours
     */
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
        doReloadHours();
    }

    /**
     * Chaecks wheter the opening times are valid
     *
     * @return
     */
    private boolean validHours(){
        if(!openingHours.getStartTime().before(openingHours.getEndTime())){
            return false;
        } else if (openingHours.getStartPause() == null || openingHours.getEndPause() == null){
            return (openingHours.getStartPause() == null && openingHours.getEndPause() == null);
        } else {
            return openingHours.getStartTime().before(openingHours.getStartPause())
                && openingHours.getStartPause().before(openingHours.getEndPause())
                && openingHours.getEndPause().before(openingHours.getEndTime());
        }
    }

    /**
     * Returns the currently displayed day.
     *
     * @return
     */
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    /**
     * Action to force a reload of the currently displayed day.
     */
    public void doReloadHours() {
        openingHours = openingHoursService.loadOpeningHour(openingHours.getDay());
    }

    /**
     * Action to save the currently displayed day.
     */
    public void doSaveHours() {
        if(validHours()){
            openingHours = this.openingHoursService.saveOpeningHour(openingHours);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Success", "Öffnungszeiten erfolgreich angepasst.")
            );
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Die eingegebenen Öffungszeiten sind ungülitg.")
            );
        }
    }

}
