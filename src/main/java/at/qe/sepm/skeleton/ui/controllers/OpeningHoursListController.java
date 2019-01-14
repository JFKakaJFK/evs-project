package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@Scope("view")
public class OpeningHoursListController {

    @Autowired
    private OpeningHoursService openingHoursService;

    /**
     * Returns a list of all openingHours.
     *
     * @return
     */
    public Collection<OpeningHours> getOpeningHours() {
        return openingHoursService.getAllOpeningHours();
    }

}
