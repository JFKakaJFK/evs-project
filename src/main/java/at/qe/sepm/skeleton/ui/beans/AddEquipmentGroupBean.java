package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * Session information bean to retrieve session-specific parameters.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Softwaredevelopment and Project Management" offered by the University
 * of Innsbruck.
 */
@Component
@Scope("session")
public class AddEquipmentGroupBean {

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    private String name;
    private Equipment equipment;
    private List<Equipment> equipments = new ArrayList<>();

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public void addEquipmentGroup(){
        if(equipments.size() >= 2){
            EquipmentGroup equipmentGroup = new EquipmentGroup();

            equipmentGroup.setName(name);
            equipmentGroup.getEquipments().addAll(equipments);

            equipmentGroupService.saveEquipmentGroup(equipmentGroup);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Gruppe " + equipmentGroup.getName() +  " wurde erfolgreich erstellt.")
            );
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Zu wenig Geräte in Gruppe")
            );
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Collection<Equipment> equipment) {
        this.equipments.addAll(equipment);
    }
}
