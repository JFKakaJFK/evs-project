package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean for adding a new {@link Equipment}
 */
@Component
@Scope("request")
public class AddEquipmentBean {

    @Autowired
    private EquipmentService equipmentService;

    private String name;
    private String labName;
    private String labLocation;
    private boolean locked;
    private String maxDuration;

    /**
     * Creates and persists a new reservation
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addEquipment(){
        Equipment equipment = new Equipment();

        equipment.setName(name);
        equipment.setLabName(labName);
        equipment.setLabLocation(labLocation);
        equipment.setLocked(locked);
        equipment.setMaxDuration(maxDuration);

        equipmentService.saveEquipment(equipment);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Gerät erfolgreich erstellt")
        );
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
}
