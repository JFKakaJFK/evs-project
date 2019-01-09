package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.ReservationType;
import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

/**
 * Controller for add-equipmentReservation functionality.
 */

@Component
@Scope("request")
public class AddEquipmentReservationController {
	
	private EquipmentReservationService equipmentReservationService;
	
    private int id;
    private String name;
    private Set<Equipment> equipment;
    private User user;
    private Date startDate;
    private Date endDate;
    private Date createDate;

    
    
    public void addEquipmentReservation() throws IOException {
        String title = "Add Reservation";
        String msg;
       EquipmentReservation equipmentReservation = new EquipmentReservation();

        if(isAvailable() && validateDate()) {
          // equipmentReservation.setName(this.name);
           equipmentReservation.setEquipment(this.equipment);
           equipmentReservation.setUser(this.user);
           equipmentReservation.setStartDate(this.startDate);
           equipmentReservation.setEndDate(this.endDate);
           equipmentReservation.setCreateDate(this.createDate);
           msg = "Reservation added successfully";
           FacesContext.getCurrentInstance().getExternalContext().redirect("reservations.xhtml?addedSuccessfully");
            }

        else {
            msg = "The equipment " + this.name + " is not available";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public boolean isAvailable() {
    	Collection<EquipmentReservation> allEquipmentReservations = new ArrayList<EquipmentReservation>();
    	allEquipmentReservations = equipmentReservationService.getAllEquipmentReservations();
    	for(EquipmentReservation er : allEquipmentReservations) {
    		if (!((this.getStartDate().after(er.getEndDate())) || (this.getEndDate().before(er.getStartDate())))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * check if enddate is after startdate and after current date
     * @return true if dates are valid
     */
    
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
    
    
    public String getEquipmentAsString(){
        return equipment.toString().substring(1, equipment.toString().length() - 1);
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
}
