package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@Scope("view")
public class AddEquipmentManualController {

    @Autowired
    private EquipmentService equipmentService;

    private String name;
    private UploadedFile file;
    private String desc;

    private boolean added = false;
    private boolean uploaded = false;

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addEquipmentManual() throws IOException {
        System.out.println(file.getFileName());

        EquipmentManual manual = new EquipmentManual();


        InputStream is = file.getInputstream();
        manual.setFile(StreamUtils.copyToByteArray(is));

        manual.setName(name);
        manual.setType(file.getContentType());
        manual.setFilename(file.getFileName());
        manual.setDesc(desc);

        // TODO no get params
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        manual.setEquipment(equipmentService.loadEquipment(Integer.valueOf(params.get("id"))));

        manual.getEquipment().addManual(manual);
        this.equipmentService.saveEquipment(manual.getEquipment());

        FacesContext.getCurrentInstance().getExternalContext().redirect("equipment-overview.xhtml");
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws IOException {
        System.out.println("submit" + file);

        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        added = true;
        if(uploaded){
            addEquipmentManual();
            uploaded = false;
            added = false;
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        System.out.println("submit" + event.getFile().getFileName());

        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        uploaded = true;
        if(added){
            addEquipmentManual();
            uploaded = false;
            added = false;
        }

    }
}
