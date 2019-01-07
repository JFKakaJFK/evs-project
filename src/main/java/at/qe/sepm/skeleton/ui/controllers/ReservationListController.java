package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
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
	private EquipmentReservationService equipmentReservationService;


	public Collection<EquipmentReservation> getReservations(){
		return equipmentReservationService.getAllEquipmentReservations();
	}
}
