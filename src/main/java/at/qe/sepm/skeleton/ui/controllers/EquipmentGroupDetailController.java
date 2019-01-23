package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing {@link EquipmentGroup} entities
 */
@Component
@Scope("view")
public class EquipmentGroupDetailController {

    @Autowired
    private EquipmentGroupService groupService;

    private EquipmentGroup group;

    private List<Equipment> selectedEquipments;

    private List<Equipment> filteredEquipments;

    public List<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(List<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public void doDeleteGroup(){
        groupService.deleteEquipmentGroup(group);
        group = null;
    }

    public void doSaveGroup(){
        List<Equipment> old = new ArrayList<>(group.getEquipments());
        for(Equipment equipment: old){
            if(!selectedEquipments.contains(equipment)){
                group.getEquipments().remove(equipment);
            }
        }
        for(Equipment equipment: selectedEquipments){
            if(!group.getEquipments().contains(equipment)){
                group.getEquipments().add(equipment);
            }
        }

        if(group.getEquipments().size() >= 2){
            group = this.groupService.saveEquipmentGroup(this.group);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Gruppe erfolgreich geändert")
            );
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", "Zu wenig Geräte in der Gruppe")
            );
        }
    }

    public void doReloadGroup(){
        group = groupService.loadEquipmentGroup(group.getId());
        this.selectedEquipments = new ArrayList<>();
        this.selectedEquipments.addAll(group.getEquipments());
    }

    public EquipmentGroup getGroup() {
        return group;
    }

    public void setGroup(EquipmentGroup group) {
        this.group = group;
        this.selectedEquipments = new ArrayList<>();
        this.selectedEquipments.addAll(group.getEquipments());
    }

    public List<Equipment> getSelectedEquipments() {
        return selectedEquipments;
    }

    public void setSelectedEquipments(List<Equipment> selectedEquipments) {
        this.selectedEquipments = selectedEquipments;
    }
}
