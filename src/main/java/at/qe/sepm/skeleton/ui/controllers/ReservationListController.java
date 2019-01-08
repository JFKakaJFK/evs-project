package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ReservationListController {
	@Autowired
	private EquipmentReservationService equipmentReservationService;

	@Autowired
    private UserService userService;


	public Collection<EquipmentReservation> getReservations(){
		return equipmentReservationService.getAllEquipmentReservations();
	}

	public Collection<EquipmentReservation> getAllUserReservations()
    {
        User authUser = userService.getAuthenticatedUser();

        return equipmentReservationService.getAllByUser(authUser);
    }

    public Collection<EquipmentReservation> getAllReservationsReturn()
    {
        return equipmentReservationService.getAllEquipmentReservations();
    }
}
