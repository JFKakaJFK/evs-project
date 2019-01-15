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
 * Bean for adding a new {@link EquipmentGroup}
 */
@Component
@Scope("request")
public class AddEquipmentGroupBean {

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    private String name;
    private Equipment equipment;
    private List<Equipment> equipments = new ArrayList<>();
    private List<Equipment> filteredEquipments;

    /**
     * Creates and persists a new {@link EquipmentGroup}
     */
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
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Sie haben zu wenig Geräte ausgewählt.")
            );
        }
    }

    public List<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(List<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
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
