package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.exceptions.UserDeletionException;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the user detail view.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * course "Softwaredevelopment and Project Management" offered by the University
 * of Innsbruck.
 */
@Component
@Scope("view")
public class UserDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    /**
     * Attribute to cache the currently displayed user
     */
    private User user;

    /**
     * Attribute to cache the current filtered users
     */
    private List<User> filteredUsers;

    private List<SelectItem> roleList = loadRoleList();

    private static List<SelectItem> loadRoleList() {
        List<SelectItem> items = new ArrayList<>();
        items.add(new SelectItem("ADMIN", "Administrator"));
        items.add(new SelectItem("EMPLOYEE", "Mitarbeiter"));
        items.add(new SelectItem("STUDENT", "Student"));
        return items;
    }

    public List<SelectItem> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SelectItem> roleList) {
        this.roleList = roleList;
    }

    /**
     * Returns the currently filtered users
     *
     * @return
     */
    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    /**
     * Sets the currently filtered users
     *
     * @param filteredUsers
     */
    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    /**
     * Sets the currently displayed user and reloads it form db. This user is
     * targeted by any further calls of
     * {@link #doReloadUser()}, {@link #doSaveUser()} and
     * {@link #doDeleteUser()}.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        doReloadUser();
    }

    /**
     * Returns the currently displayed user.
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadUser() {
        user = userService.loadUser(user.getUsername());
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveUser() {
        // TODO: test if the current user can lock him/herself
        if(sessionInfoBean.getCurrentUser() == user && !user.isEnabled()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", "Du kannst dich nicht selbst sperren.")
            );
        } else {
            user = this.userService.saveUser(user);
        }
    }

    /**
     * Action to delete the currently displayed user.
     */
    public void doDeleteUser() {
        try{
            this.userService.deleteUser(user);
        } catch (UserDeletionException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error", e.getMessage())
            );
        }
        user = null;
    }

}
