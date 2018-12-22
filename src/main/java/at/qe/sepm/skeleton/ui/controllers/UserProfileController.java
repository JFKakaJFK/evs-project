package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;

/**
 * Controller for password changing.
 */

@Component
@Scope("request")
public class UserProfileController {

    @Autowired
    private SessionInfoBean sessionInfoBean;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

    public void changePassword() {
        User currentUser = sessionInfoBean.getCurrentUser();
        String title = "Password";
        String msg;

        if(passwordEncoder.matches(oldPassword, currentUser.getPassword()) && repeatNewPassword.equals(newPassword) && validatePW(newPassword)) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(currentUser);
            msg = "Password changed successfully";

        } 
        // special password length and or letters?
        else if(!(newPassword.length() <= 60)) {
            msg = "Password too long";

        } else if(!(newPassword.length() >= 6)) {
            msg = "Password too short";

        } else if(!newPassword.matches(".*\\d.*")) {
            msg = "Password has to contain at least one number";

        } else if(!newPassword.matches(".*[A-Z].*")) {
            msg = "Password has to contain at least one capital letter";

        } else {
            msg = "Password change failed";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

    //Check if the new Password is valid (length and letter)
    
    private boolean validatePW(String pw) {
        return (pw.length() > 6 && pw.length() < 60 && pw.matches(".*\\d.*") && pw.matches(".*[A-Z].*"));
    }

  //Getter and Setter for the Password
    
    public void setOldPassword(String password){
        this.oldPassword = password;
    }

    public  void  setNewPassword(String password){
        this.newPassword = password;
    }

    public String getOldPassword(){
        return this.oldPassword;
    }

    public String getNewPassword(){
        return this.newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
}
