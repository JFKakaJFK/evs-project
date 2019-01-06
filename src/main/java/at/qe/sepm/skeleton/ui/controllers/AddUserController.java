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
    private String email;
    private String cNumber;
    private boolean enabled;
    private boolean addedSuccessfully = false;
    private Date dob;
    private Set<UserRole> roles;
    private boolean admin;
    private boolean employee;
    private boolean student;


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
     * Check if the csav is valid
     * @param cId that should be validated
     * @return true if cId is valid else false
     */

    public boolean validateCId(String cNumber){
        String regexC = "(cs)[a-z][a-z]\\d\\d\\d\\d$"; //(cs)*(small-case letter)*(small-caseletter)*(any 5-digit number)
        Pattern patternC = Pattern.compile(regexC);
        Matcher matcherC = patternC.matcher(cNumber);
        return matcherC.matches();
    }

    /**
     * Create new user
     */

    // creates a new User and checks if the values are valid

    public void addUser() throws IOException {
        String title = "Add User";
        String msg;

        User addedUser = new User();

        /**
         * According to your needs you can decide whether you want to validate
         * the email or the c-Id. If there are any issues I will adapt the class accordingly.
         */


        if(validateEmail(this.email) && this.email != null) {
            addedUser.setUsername(username);
            addedUser.setPassword(passwordEncoder.encode(password));
            addedUser.setFirstName(this.firstName);
            addedUser.setLastName(this.lastName);
            /**addedUser.setCNumber(this.cNumber);*/ //Csav!!!
            addedUser.setEmail(this.email);
            addedUser.setEnabled(this.enabled);
            //don't know if needed
            /**
             if(validateCId(this.cId) == false) {
             msg = "Please verify your c-Id";
             }
             */
            if(userService.getAllUsers().contains(this.username)) {
                //here one could also specify,that the same condition holds if a username has deleted and someone tries to pick it again
                msg = "Please choose another username";
            }else {
                this.userService.saveUser(addedUser);
                msg = "User added successfully";
                FacesContext.getCurrentInstance().getExternalContext().redirect("users.xhtml?addedSuccessfully");
            }

        }else {
            msg = "The email address is invalid"; //or c-Id
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

    public String getCNumber(){
        return cNumber;
    }

    public void setCNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public boolean isEmployee(){
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    public void setAddedSuccessfully(boolean addedSuccessfully) {
        this.addedSuccessfully = addedSuccessfully;
    }

    private void convertRoles() {
        roles = new HashSet<>();
        if(student) { roles.add(UserRole.STUDENT); }
        if(employee) { roles.add(UserRole.EMPLOYEE); }
        if(admin) { roles.add(UserRole.ADMIN); }
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
