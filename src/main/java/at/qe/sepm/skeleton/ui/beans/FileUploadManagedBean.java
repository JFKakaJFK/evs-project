package at.qe.sepm.skeleton.ui.beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.servlet.http.Part;
@ManagedBean
@ViewScoped
public class FileUploadManagedBean {

    private Part uploadedFile;
    private String folder = "//resources//";

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }


    public void saveFile(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", uploadedFile.getSubmittedFileName()));

        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();
            Files.copy(input, new File(folder, fileName).toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

