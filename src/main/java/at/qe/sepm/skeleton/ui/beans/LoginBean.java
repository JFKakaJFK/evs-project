package at.qe.sepm.skeleton.ui.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * A bean for checking whether to display a session expired message
 */
@Controller
@Scope("request")
public class LoginBean {

    /**
     * Checks the url for an "expired" get parameter
     */
    public void checkURL() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("expired")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ihre Sitzung ist abgelaufen.\n Bitte melden Sie sich erneut an."));
        }
        if (params.containsKey("error")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ihr Benutzername oder Passwort stimmt nicht."));
        }
    }
}
