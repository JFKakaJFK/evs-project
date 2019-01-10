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
    private EquipmentReservation equipmentReservation;
    private Integer reservationId;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
        doReloadReservation();
    }

    /**
     * Action to force a reload of the currently displayed reservation.
     */
    public void doReloadReservation() {
        this.equipmentReservation = this.equipmentReservationService.loadRerservation(this.reservationId);
    }

    /**
     * Action to delete the currently displayed reservation.
     */
    public void doDeleteReservation() {
        this.equipmentReservationService.deleteReservation(equipmentReservation);
        equipmentReservation = null;
    }
}
