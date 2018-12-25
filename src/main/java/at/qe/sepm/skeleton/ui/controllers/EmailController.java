package at.qe.sepm.skeleton.ui.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;
/**
 * 
 * @author markus
 * Controller for booking tools via email
 */

@Controller //Using "@Controller" instead of simple "@Component" annotation 
@Scope("notify")
public class EmailController {
	
	private Equipment equipment;
	if(equipment.equipmentState == Equipmentstate.EXPIRED){
		//TODO: Send an Email if the booking is overdue 
	}
	if(equipment.equipmentState == Equipmentstate.BOOKED){
		//TODO: Booking successful
	}
	if(equipment.equipmentState == Equipmentstate.AVAILABLE || equipment.equipmentState == Equipmentstate.AVAILABLE){
		//TODO: Booking unsuccessful
	}
	
}
