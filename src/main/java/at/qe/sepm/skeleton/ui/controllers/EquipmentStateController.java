package at.qe.sepm.skeleton.ui.controllers;


/**
 * Controller used for changing the equipment state
 */

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import at.qe.sepm.skeleton.model.EquipmentState;
import at.qe.sepm.skeleton.model.EquipmentState.Equipmentstate;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * 
 * @author markus
 * Class to change the state of the Equipment
 */


@Controller
@Scope("request")
public class EquipmentStateController {
	
	@Autowired
	private Equipmentstate equipmentState;
	
	
	
	public void setBooked(EquipmentState equipmentState){
		this.equipmentState = Equipmentstate.BOOKED;
	}
	
	public void setExpired(EquipmentState equipmentState){
		this.equipmentState = Equipmentstate.EXPIRED;
	}
	
	public void setAvailable(EquipmentState equipmentState){
		this.equipmentState = Equipmentstate.AVAILABLE;
	}
	
	public void setLocked(EquipmentState equipmentState){
		this.equipmentState = Equipmentstate.LOCKED;
	}
	
	public Equipmentstate getEquipmentState(){
		return equipmentState;
	}
	
	
	/**
	 * 
	 * This might require a class of its own. For now I shall put this here.
	 */
	
	public boolean isAvailable(EquipmentState equipmentState){
		if(this.equipmentState == Equipmentstate.AVAILABLE){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isBooked(EquipmentState equipmentState){
		if(this.equipmentState == Equipmentstate.BOOKED){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isExpired(EquipmentState equipmentState){
		if(this.equipmentState == Equipmentstate.EXPIRED){
			return true;
		}
		else{
			return false;
		}
	}
		
	public boolean isLocked(EquipmentState equipmentState){
		if(this.equipmentState == Equipmentstate.LOCKED){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
