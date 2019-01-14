package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.services.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@Scope("view")
public class HolidaysListController {

    @Autowired
    private HolidaysService holidaysService;

    public Collection<Holidays> getAll(){
        return holidaysService.getAllHolidays();
    }
}
