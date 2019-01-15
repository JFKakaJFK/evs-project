package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

/**
 * Controller for equipment overview view
 */
@Controller
@Scope("view")
public class EquipmentListController {

    @Autowired
    private EquipmentService equipmentService;

    /**
     * Returns all stored equipment
     *
     * @return
     */
    public Collection<Equipment> getEquipment() {
        return equipmentService.getAllEquipments();
    }

}
