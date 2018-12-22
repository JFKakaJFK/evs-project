package at.qe.sepm.skeleton.ui.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * Controller for handling URL parameters of Login page.
 */

@Component
@Scope("request")
public class LoginPageController {

    private boolean error = false;
    private boolean loggedOut = false;

    public void handleParameters() throws IOException {
        Iterator<String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterNames();

        if(params.hasNext()) {
            String parameter = params.next();
            if(parameter.equals("error")) {
                this.error = true;
            } else if(parameter.equals("logout")){
                this.loggedOut = true;
            }
        }
    }
    
    //logout user
    
    public void doLogOut() throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/logout");
        }
    }

    public boolean isError(){
        return this.error;
    }

    public boolean isLoggedOut(){
        return this.loggedOut;
    }
}
