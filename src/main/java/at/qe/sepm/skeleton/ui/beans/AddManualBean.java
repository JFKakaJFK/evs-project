package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean for add equipment manual functionality.
 */

@Component
@Scope("request")
public class AddManualBean {

    @Autowired
    private EquipmentService equipmentService;

    private String name;
    private String desc;
    private Equipment equipment;

    private EquipmentManual manual;

    public void handleFileUpload(FileUploadEvent event){
        System.out.println("Was Called");
        System.out.println(event);
        System.out.println(event.getFile());
        System.out.println(event.getFile().getFileName());
        this.manual = new EquipmentManual();
        manual.setEquipment(equipment);
        manual.setFilename(event.getFile().getFileName());
        manual.setType(event.getFile().getContentType());
        manual.setFile(event.getFile().getContents());

        manual.setName("name");

        equipmentService.saveManual(manual);

        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage("growla", msg);
    }

    public void addManual(){
        if(manual == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Sie müssen zuerst eine Datei auswählen!")
            );
            return;
        }

        manual.setName(name);
        manual.setDesc(desc);

        equipmentService.saveManual(manual);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Betriebsanleitung '" + manual.getFilename() + "' erfolgreich gespeichert")
        );

        this.manual = null;
    }

    public void setEquipment(Equipment equipment){
        this.equipment = equipment;
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
