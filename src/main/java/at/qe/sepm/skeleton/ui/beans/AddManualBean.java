package at.qe.sepm.skeleton.ui.beans;

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

    private UploadedFile file;
    private String name;
    private String desc;

    public void upload(){
        if(file != null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Success", "Betriebsanleitung erfolgreich hochgeladen")
            );

            System.out.println("SUCCESS");
        }
        System.out.println("FAIL");
    }

    public void handleFileUpload(FileUploadEvent event){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Success", "Betriebsanleitung erfolgreich hochgeladen")
        );

        System.out.println("SUCCESS");
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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
