package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean for adding a new {@link EquipmentComment}
 */
@ManagedBean
public class AddEquipmentCommentBean {

    @Autowired
    private EquipmentService equipmentService;

    private String title;
    private String message;
    private Equipment equipment;

    /**
     * Creates and persists a new {@link EquipmentComment}
     */
    public void addComment(){
        EquipmentComment comment = new EquipmentComment();
        comment.setTitle(title);
        comment.setMessage(message);
        equipment.addComment(comment);
        equipmentService.saveComment(comment);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Bemerkung erfolgreich gespeichert.")
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
