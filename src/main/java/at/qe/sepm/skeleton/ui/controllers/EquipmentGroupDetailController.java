package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Component
@Scope("view")
public class EquipmentGroupDetailController {

    @Autowired
    private EquipmentGroupService groupService;

    private EquipmentGroup group;

    public void doDeleteGroup(){
        groupService.deleteEquipmentGroup(group);
        group = null;
    }

    public void doSaveGroup(){
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
    }

    public EquipmentGroup getGroup() {
        return group;
    }

    public void setGroup(EquipmentGroup group) {
        this.group = group;
    }
}
