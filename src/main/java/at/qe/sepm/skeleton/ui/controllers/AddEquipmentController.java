package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentState;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.services.EquipmentService;
//import at.qe.sepm.skeleton.model.EquipmentReservation;
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

    @Autowired
    private EquipmentService equipmentService;

    private String name;
    private String labName;
    private String labLocation;
    private boolean locked;
    private String maxDuration;
    private boolean addedSuccessfully = false;

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addEquipment() throws IOException {
        String title = "Add Equipment";
        String msg;
        Equipment equipment = new Equipment();

        equipment.setName(this.name);
        equipment.setLabName(this.labName);
        equipment.setLabLocation(this.labLocation);
        equipment.setLocked(this.locked);
        equipment.setMaxDuration(this.maxDuration);

        this.equipmentService.saveEquipmnet(equipment);

        msg = "Equipment added successfully";
        FacesContext.getCurrentInstance().getExternalContext().redirect("equipment-overview.xhtml?addedSuccessfully");

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    public void setAddedSuccessfully(boolean addedSuccessfully) {
        this.addedSuccessfully = addedSuccessfully;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }

    /**
     * Check URL in order to know if user was added successfully.
     * In that case a growl success message will be displayed.
     */

    public void checkURL() {
        Iterator<String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterNames();
        if(params.hasNext()) {
            String parameter = params.next();
            if(parameter.equals("addedSuccessfully")) {
                this.addedSuccessfully = true;
            }
        }
    }
}

