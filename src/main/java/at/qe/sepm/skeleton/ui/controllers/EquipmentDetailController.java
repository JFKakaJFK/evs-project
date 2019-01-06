package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.util.Map;

@Component
@Scope("view")
public class EquipmentDetailController {

    @Autowired
    private EquipmentService equipmentService;

    private Equipment equipment;
    private EquipmentComment comment;
    private EquipmentManual manual;

    // TODO
    @Deprecated
    public void loadEquipmentByURL(){
        Map<String, String> params =FacesContext.getCurrentInstance().
            getExternalContext().getRequestParameterMap();
        System.out.println(params.getOrDefault("id", "404"));
        Integer id = Integer.valueOf(params.get("id"));
        this.equipment = equipmentService.loadEquipment(id);
    }

    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
        doReloadEquipment();
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public EquipmentComment getComment() {
        return comment;
    }

    public void setComment(EquipmentComment comment) {
        this.comment = comment;
    }

    public EquipmentManual getManual() {
        return manual;
    }

    public void setManual(EquipmentManual manual) {
        this.manual = manual;
    }

    public void doReloadEquipment(){
        equipment = equipmentService.loadEquipment(equipment.getId());
    }

    public void doSaveEquipment(){
        equipment = this.equipmentService.saveEquipment(this.equipment);
    }

    public void doDeleteEquipment(){
        this.equipmentService.deleteEquipment(equipment);
        this.equipment = null;
    }

    public void doReloadComment(){
        comment = equipmentService.loadComment(comment.getId());
    }

    public void doSaveComment(){
        comment = this.equipmentService.saveComment(this.comment);
    }

    public void doDeleteComment(){
        this.equipmentService.deleteComment(comment);
        this.comment = null;
    }

    public void doReloadManual(){
        manual = equipmentService.loadManual(manual.getId());
    }

    public void doSaveManual(){
        manual = this.equipmentService.saveManual(this.manual);
    }

    public void doDeleteManual(){
        this.equipmentService.deleteManual(manual);
        this.manual = null;
    }
}
