package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean to add a new user.
 */
@Component
@Scope("session")
public class AddUserBean {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String cNumber;
    private boolean enabled;
    private String role;

    /**
     * Creates and persists a new user
     */
    public void addUser(){
        if(!validateEmail()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "E-Mail ist ungültig.")
            );
            return;
        }

        if(!validateCNumber()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "C-Kennung ist ungültig.")
            );
            return;
        }

        if(userService.loadUser(username) != null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Benutzername ist bereits vergeben.")
            );
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setcNumber(cNumber);
        user.setEnabled(enabled);
        user.setRoles(user.determineRoles(role));

        userService.saveUser(user);
    }

    /**
     * Check if the csav is valid
     *
     * @return true if cId is valid else false
     */

    public boolean validateCNumber(){
        if(cNumber == null){
            return false;
        }
        String regexC = "^(c)...\\d\\d\\d\\d$";
        Pattern patternC = Pattern.compile(regexC);
        Matcher matcherC = patternC.matcher(cNumber);
        return matcherC.matches();
    }

    /**
     * Check if email address is valid
     *
     * @return true if email is valid else false
     */
    private boolean validateEmail(){
        if(email == null){
            return false;
        }
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /* Getters & Setters */

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
