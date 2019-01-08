package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@Controller
@Scope("view")
public class EquipmentListController {

    @Autowired
    private EquipmentService equipmentService;

    private List<Equipment> filteredEquipments;

    public List<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(List<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public Collection<Equipment> getEquipment() {
        return equipmentService.getAllEquipments();
    }

}
