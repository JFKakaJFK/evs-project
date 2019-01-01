package at.qe.sepm.skeleton.ui.controllers;

//import at.qe.sepm.skeleton.model.EquipmentReservation;
//import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.User;
//import at.qe.sepm.skeleton.model.EquipmentReservationRole;
//import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.UserService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for add-equipmentReservation functionality.
 */

/*
@Component
@Scope("request")
public class AddEquipmentReservationController {

	private int id;
    private Set<Equipment> equipment;
    private User user;
    private String groupName;
    private Date startDate;
    private Date endDate;


    public void addEquipmentReservation() throws IOException {
        String title = "Add Reservation";
        String msg;
        Date today = new Date();
        today.getTime();
       EquipmentReservation equipmentReservation = new EquipmentReservation();

        if(isAvailable() && validateDate()) {
           equipmentReservation.setId(this.id);
           equipmentReservation.setEquipment(this.equipment);
           equipmentReservation.setUser(this.user);
           equipmentReservation.setGroupName(this.groupName);
           equipmentReservation.setStartDate(this.startDate);
           equipmentReservation.setEndDate(this.endDate);
           msg = "Reservation added successfully";
           FacesContext.getCurrentInstance().getExternalContext().redirect("reservations.xhtml?addedSuccessfully");
            }

        else {
            msg = "The equipment(s) is/are not available";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public boolean isAvailable() {
    	// TODO
    	return false;
    }


	public boolean validateDate() {
		Date today = new Date();
		today.getTime();
		if(today.after(this.endDate) && today.after(this.startDate)) {
			return false;
		}
		if(endDate.before(startDate)) {
			return false;
		}
		return true;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEquipment(Set<Equipment> equipment) {
		this.equipment = equipment;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
*/
