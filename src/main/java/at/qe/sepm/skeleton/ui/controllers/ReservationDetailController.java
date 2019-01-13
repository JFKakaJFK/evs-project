package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.util.List;

@Component
@Scope("view")
public class ReservationDetailController {
    private List<EquipmentReservation> selectedReservationsReturn;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    /**
     * Attribute to cache the currently displayed reservation
     */
    private EquipmentReservation equipmentReservation;

    public EquipmentReservation getEquipmentReservation() {
        return equipmentReservation;
    }

    public void setEquipmentReservation(EquipmentReservation equipmentReservation) {
        this.equipmentReservation = equipmentReservation;
        doReloadReservation();
    }

    /**
     * Action to force a reload of the currently displayed reservation.
     */
    public void doReloadReservation() {
        this.equipmentReservation = this.equipmentReservationService.loadRerservation(
            this.equipmentReservation.getId());
    }

    /**
     * Action to delete the currently displayed reservation.
     */
    public void doDeleteReservation() {
        if(equipmentReservation != null)
        {
            this.equipmentReservationService.deleteReservation(equipmentReservation);
            equipmentReservation = null;
        }
    }
}
