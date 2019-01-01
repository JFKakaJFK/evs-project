package at.qe.sepm.skeleton.ui.beans;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean for managing Growl messages.
 */

@ManagedBean
public class GrowlBean {

    private String message;

    public void showMessage(String message){
        this.message = message;
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("", this.message));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
