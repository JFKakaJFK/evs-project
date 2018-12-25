package at.qe.sepm.skeleton.ui.controllers;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Scope("view")



public class ViewStateController {
	
	@Autowired
	EquipmentService equipmentService;
	
	/**
	 * Controller used for getting every equipment of a certain state
	 */
	
	/**
	 * 
	 * for the following methods you need to create a method that
	 * returns all equipment in their state accordingly 
	 */
	public Collection<Equipment> getAllExpired(){ 
		return equipmentService.expEquipment();
	}
	
	public Collection<Equipment> getAllBooked(){ 
		return equipmentService.bookedEquipment();
	}
	
	public Collection<Equipment> getAllAvailable(){ 
		return equipmentService.avEquipment();
	}
	
	public Collection<Equipment> getAllLocked(){ 
		return equipmentService.lockedEquipment();
	}
	
}

