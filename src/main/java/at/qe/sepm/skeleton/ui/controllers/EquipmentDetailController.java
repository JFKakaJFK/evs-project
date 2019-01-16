package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.exceptions.EquipmentDeletionException;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * A controller for managing {@link Equipment}, {@link EquipmentComment} and {@link EquipmentManual} entities
 */
@Component
@Scope("view")
public class EquipmentDetailController {

    @Autowired
    private EquipmentService equipmentService;

    private Equipment equipment;
    private EquipmentComment comment;
    private EquipmentManual manual;
    private Integer expandedRowElementId = 1;

    /**
     * Reloads a persisted entity
     */
    public void doReloadEquipment(){
        equipment = equipmentService.loadEquipment(equipment.getId());
    }

    /**
     * Persists changes to the entity
     */
    public void doSaveEquipment(){
        equipment = this.equipmentService.saveEquipment(this.equipment);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Gerät erfolgreich gespeichert.")
        );
    }

    /**
     * Deletes an {@link Equipment} if possible
     */
    public void doDeleteEquipment(){
        try{
            this.equipmentService.deleteEquipment(equipment);
        } catch (EquipmentDeletionException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", e.getMessage())
            );
        }

        this.equipment = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Gerät erfolgreich gelöscht.")
        );
    }

    /**
     * Reloads a persisted entity
     */
    public void doReloadComment(){
        comment = equipmentService.loadComment(comment.getId());
    }

    /**
     * Persists changes to the entity
     */
    public void doSaveComment(){
        comment = this.equipmentService.saveComment(this.comment);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Bemerkung erfolgreich gespeichert.")
        );
    }

    /**
     * Deletes an {@link EquipmentComment}
     */
    public void doDeleteComment(){
        equipmentService.deleteComment(comment);
        comment = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Bemerkung erfolgreich gelöscht.")
        );
    }

    /**
     * Deletes an {@link EquipmentManual}
     */
    public void doDeleteManual(){
        equipmentService.deleteManual(manual);
        manual = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Bedienungsanleitung erfolgreich gelöscht.")
        );
    }

    public Integer getExpandedRowElementId() {
        return expandedRowElementId;
    }

    public void setExpandedRowElementId(Integer expandedRowElementId) {
        this.expandedRowElementId = expandedRowElementId;
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
}
