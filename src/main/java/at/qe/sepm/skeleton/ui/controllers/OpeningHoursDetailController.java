package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


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
     * {@link #doDeleteHours()}.
     *
     * @param openingHours
     */
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
        doReloadHours();
    }

    /**
     * Returns the currently displayed user.
     *
     * @return
     */
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadHours() {
        openingHours = openingHoursService.loadOpeningHour(openingHours.getDay());
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveHours() {
        openingHours = this.openingHoursService.saveOpeningHour(openingHours);
    }

    /**
     * Action to delete the currently displayed user.
     */
    public void doDeleteHours() {
        this.openingHoursService.deleteOpeningHour(openingHours);
        openingHours = null;
    }

}
