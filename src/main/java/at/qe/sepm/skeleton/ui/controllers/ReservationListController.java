package at.qe.sepm.skeleton.ui.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ReservationListController {
	@Autowired
	private ReservationService reservationService;
	
	
	public Collection<Reservation> getReservations(){
		return reservationService.getAllReservations();
	}
}
