package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ReservationListController {
	@Autowired
	private EquipmentReservationService equipmentReservationService;

	@Autowired
    private EquipmentService equipmentService;

	@Autowired
    private UserService userService;

	//
    private List<EquipmentReservation> selectedReservations;


    public List<EquipmentReservation> getSelectedReservations() {
        return selectedReservations;
    }

    public void setSelectedReservations(List<EquipmentReservation> selectedReservations) {
        this.selectedReservations = selectedReservations;
    }

    public Collection<EquipmentReservation> getReservations(){
		return equipmentReservationService.getAllEquipmentReservations();
	}

	public Collection<EquipmentReservation> getAllUserReservations()
    {
        User authUser = userService.getAuthenticatedUser();

        return equipmentReservationService.getAllByUser(authUser);
    }

    public Collection<Equipment> getAllReservationsReturn()
    {
        return equipmentReservationService.getAllBorrowedEquipments();
    }
}
