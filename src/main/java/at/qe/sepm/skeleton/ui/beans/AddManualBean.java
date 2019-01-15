package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.StorageService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Bean for add {@link EquipmentManual} functionality.
 */
@ManagedBean
public class AddManualBean {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private StorageService storageService;

    private String name;
    private String desc;
    private String originalFileName;
    private String type;
    private String filename = null;
    private Equipment equipment;

    /**
     * Catches a fileupload and stores file
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event){

        originalFileName = event.getFile().getFileName();
        type = event.getFile().getContentType();

        try {
            filename = storageService.store(event.getFile());
        } catch (IOException e){
            filename = null;
        }
    }

    /**
     * Creates and persists a new {@link EquipmentManual}
     */
    public void addManual(){
        if(filename == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Sie müssen zuerst eine Datei auswählen!")
            );
            return;
        }

        EquipmentManual manual = new EquipmentManual();

        manual.setName(name);
        manual.setDesc(desc);
        manual.setFilename(filename);
        manual.setType(type);
        manual.setOriginalFileName(originalFileName);

        manual.setEquipment(equipment);

        equipmentService.saveManual(manual);
        filename = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Betriebsanleitung '" + manual.getOriginalFileName() + "' erfolgreich gespeichert")
        );
    }

    /**
     * If the creation of a new {@link EquipmentManual} is aborted after uploading a file, delete the file
     *
     * @throws IOException
     */
    public void abort() throws IOException {
        if(filename != null){
            storageService.deleteFile(filename);
        }
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
