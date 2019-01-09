package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@Controller
@Scope("view")
public class EquipmentReservationListController {

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    public Collection<EquipmentReservation> getEquipmentReservation() {
        return equipmentReservationService.getAllEquipmentReservations();
    }

}