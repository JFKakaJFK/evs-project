package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.UserService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Controller for add-user functionality.
 */

@Component
@Scope("request")
public class AddUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String username;
    private String password = "Passwd1"; // default password for each new user
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean addedSuccessfully = false;

    /**
     * Check if email address is valid
     * @param email that should be validated
     * @return true if email is valid else false
     */

    public boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Create new user
     */

    // creates a new User and checks if the values are valid
    
    public void addUser() throws IOException {
        String title = "Add User";
        String msg;

        User addedUser = new User();

        if(validateEmail(this.email) && this.email != null) {
            addedUser.setUsername(username);
            addedUser.setPassword(passwordEncoder.encode(password));
            addedUser.setFirstName(this.firstName);
            addedUser.setLastName(this.lastName);
            addedUser.setEnabled(this.enabled);
            if(userService.getAllUsernames().contains(this.username) || userService.getAllDeletedUsernames().contains(this.username)) {
                msg = "Please choose another username";
            }else {
                this.userService.saveUser(addedUser);
                msg = "User added successfully";
                FacesContext.getCurrentInstance().getExternalContext().redirect("users.xhtml?addedSuccessfully");
            }

        }else {
            msg = "The email address is invalid";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    //Getter and Setter
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    public void setAddedSuccessfully(boolean addedSuccessfully) {
        this.addedSuccessfully = addedSuccessfully;
    }

    /**
     * Check URL in order to know if user was added successfully.
     * In that case a growl success message will be displayed.
     */

    public void checkURL() {
        Iterator<String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterNames();
        if(params.hasNext()) {
            String parameter = params.next();
            if(parameter.equals("addedSuccessfully")) {
                this.addedSuccessfully = true;
            }
        }
    }
}