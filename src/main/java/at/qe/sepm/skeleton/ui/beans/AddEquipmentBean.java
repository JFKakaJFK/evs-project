package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.StorageService;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean for adding a new {@link Equipment}
 */
@Component
@Scope("view")
public class AddEquipmentBean {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private StorageService storageService;

    private String name;
    private String labName;
    private String labLocation;
    private boolean locked;
    private String maxDuration;

    private String csv;

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

    /**
     * Creates new {@link Equipment} enities using a csv file
     */
    public void addEquipmentsCSV(){
        if (csv == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Sie müssen zuerst eine Datei auswählen!")
            );
            return;
        }

        try(BufferedReader buf = new BufferedReader(new InputStreamReader(Files.newInputStream(storageService.load(csv))))) {

            String line;
            try {
            	while((line = buf.readLine()) != null) {
            		String[] equipmentData = line.split(",");

            		name = equipmentData[0];
            		labName = equipmentData[1];
            		labLocation = equipmentData[2];
                    maxDuration = equipmentData[3];
            		locked = Boolean.valueOf(equipmentData[4]);



            		addEquipment();
            	}
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Success", "Die Geräte wurder erfolgreich hinzugefügt")
                );
            } catch (Exception e) {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Die CSV Datei entspricht nicht dem verlangtem Format.")
                );
            }

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Fehler beim Gerät hinzufügen")
            );
        } finally {
            try {
                storageService.deleteFile(csv);
            } catch (IOException e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Fehler beim Gerät hinzufügen")
                );
            }
            csv = null;
        }
    }

    /**
     * If the creation of a new {@link Equipment} is aborted after uploading a file, delete the file
     *
     * @throws IOException
     */
    public void abort() throws IOException {
        if(csv != null){
            storageService.deleteFile(csv);
        }
    }

    /**
     * Handles the upload of {@link Equipment} per csv file
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            csv = storageService.store(event.getFile());
        } catch (IOException e){
            csv = null;
        }
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
