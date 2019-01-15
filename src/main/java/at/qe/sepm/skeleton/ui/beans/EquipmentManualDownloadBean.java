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

/**
 * A bean for handling the download of {@link EquipmentManual}
 */
@Controller
@Scope("request")
public class EquipmentManualDownloadBean {

    @Autowired
    private StorageService storageService;

    /**
     * Downloads the {@link EquipmentManual} to the client
     *
     * @param manual to download
     * @throws IOException
     */
    public void download(EquipmentManual manual) throws IOException {
        Path p = storageService.load(manual.getFilename());

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        // prepare response headers
        ec.responseReset();
        ec.setResponseContentType(manual.getType());
        ec.setResponseContentLength(((int) p.toFile().length())); // only for the browsers download progressbar
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + manual.getOriginalFileName() + "\"");
        // prepare stream to client
        OutputStream outputStream = ec.getResponseOutputStream();
        // stream the file to the client
        Files.copy(p, outputStream);

        fc.responseComplete();
    }
}
