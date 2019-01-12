package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.services.StorageService;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@Scope("request")
public class FileDownloadBean {

    @Autowired
    private StorageService storageService;

    /*
    // PRIMEFACES VERSION
    // would need getters and setters
    private DefaultStreamedContent file;
    */

    public void download(EquipmentManual manual) throws IOException {
        Path p = storageService.load(manual.getFilename());

        System.out.println(p.getFileName());

        /*
        // PRIMEFACES VERSION
        // Works only if the uploads folder is in the src/main/webapp/** folder
        InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/upload-dir/" + manual.getFilename());
        file = new DefaultStreamedContent(input, manual.getType(), manual.getOriginalFileName());
        */

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType(manual.getType());
        ec.setResponseContentLength(((int) p.toFile().length()));
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + manual.getOriginalFileName() + "\"");

        OutputStream outputStream = ec.getResponseOutputStream();

        Files.copy(p, outputStream);

        fc.responseComplete();
    }
}
