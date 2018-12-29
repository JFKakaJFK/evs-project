package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentState;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for add-equipmentReservation functionality.
 */

@Component
@Scope("request")
public class AddEquipmentController {
	
	private Integer id;
    private String name;
    private String labName;
    private String labLocation;
    private EquipmentState state;
    private Long maxDurationMilliseconds;
    
    private List<EquipmentComment> comments;
    private List<EquipmentManual> manuals;
    private List<EquipmentReservation> reservations;

    
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addEquipment() throws IOException {
        String title = "Add Equipment";
        String msg;
       Equipment equipment = new Equipment();
       /*
        * Kompiliert nicht, weil Methoden von EquipmentService fehlen!
        * 
        if(EquipmentService.getAllEquipmentsByName().contains(this.name) || EquipmentService.getAllEquipmentsById().contains(this.id)) {
        	msg = "Equipment already exists";
        
        }else {
        	equipment.setId(this.id);
        	equipment.setName(this.name);
        	equipment.setlabName(this.labName);
        	equipment.setlabLocation(this.labLocation);
        	equipment.setState(this.state);
        	equipment.setMaxDurationMilliseconds(this.maxDurationMilliseconds);
        	equipment.setComments(this.comments);
        	equipment.setManuals(this.manuals);
        	equipment.setReservations(this.reservations);
        	
        	msg = "Equipment added successfully";
            FacesContext.getCurrentInstance().getExternalContext().redirect("reservations.xhtml?addedSuccessfully");
        }
		
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        */
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabLocation() {
        return labLocation;
    }

    public void setLabLocation(String labLocation) {
        this.labLocation = labLocation;
    }

    public EquipmentState getState() {
        return state;
    }

    public void setState(EquipmentState state) {
        this.state = state;
    }

    public Long getMaxDurationMilliseconds() {
        return maxDurationMilliseconds;
    }

    public void setMaxDurationMilliseconds(Long maxDurationMilliseconds) {
        this.maxDurationMilliseconds = maxDurationMilliseconds;
    }

    public List<EquipmentComment> getComments() {
        return comments;
    }

    public void setComments(List<EquipmentComment> comments) {
        this.comments = comments;
    }

    public List<EquipmentManual> getManuals() {
        return manuals;
    }

    public void setManuals(List<EquipmentManual> manuals) {
        this.manuals = manuals;
    }


    public Integer getId() {
        return id;
    }

}
