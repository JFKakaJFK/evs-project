package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ReservationDetailController {
    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private EquipmentService equipmentService;

    /**
     * Attribute to cache the currently displayed reservation
     */
    private EquipmentReservation reservation;

    public EquipmentReservation getEquipmentReservation() {
        return reservation;
    }

    public void setEquipmentReservation(EquipmentReservation equipmentReservation) {
        this.reservation = equipmentReservation;
        doDeleteReservation();
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadReservation() {
        //TODO reload reservation from db
        //this.equipmentReservation = this.equipmentReservationService.reloadReservation(this.equipmentReservation.getId());
    }

    /**
     * Action to delete the currently displayed reservation.
     */
    public void doDeleteReservation() {
        this.equipmentReservationService.deleteReservation(reservation);
        reservation = null;
    }
}
