package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;

@Component
@Scope("view")
public class ReservationDetailController {
    @Autowired
    private EquipmentReservationService equipmentReservationService;

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
        int idFromView = Integer.parseInt(
            FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("detailReservation"));

        this.setReservationId(idFromView);

        if(equipmentReservation != null)
        {
            this.equipmentReservationService.deleteReservation(equipmentReservation);
            equipmentReservation = null;
        }
    }
}
