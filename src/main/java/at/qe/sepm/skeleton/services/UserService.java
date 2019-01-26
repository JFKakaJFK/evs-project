package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.exceptions.ReservationInProgressException;
import at.qe.sepm.skeleton.exceptions.UserDeletionException;
import at.qe.sepm.skeleton.exceptions.UserIsAuthenticatedUserException;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Service for accessing and manipulating user data.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Softwaredevelopment and Project Management" offered by the University
 * of Innsbruck.
 */
@Component
@Scope("application")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    /**
     * Returns a collection of all users.
     *
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')") for EMAIL
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns a collection of all admin-users.
     *
     * @return
     */
    public Collection<User> getAllAdminUsers()
    {
        return userRepository.findByRole(UserRole.ADMIN);
    }

    /**
     * Loads a single user identified by its username.
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public User loadUser(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Saves the user. This method will also set {@link User#createDate} for new
     * entities or {@link User#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link User#createDate}
     * or {@link User#updateUser} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN') or (principal.username eq #user.username and hasAuthority('EMPLOYEE'))")
    public User saveUser(User user) {
        if (user.isNew()) {
            user.setCreateDate(new Date());
            user.setCreateUser(getAuthenticatedUser());
        } else {
            user.setUpdateDate(new Date());
            user.setUpdateUser(getAuthenticatedUser());
        }
        return userRepository.save(user);
    }

    /**
     * Deletes the user. A user cannot delete himself.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(User user) throws UserDeletionException {
        if(user.equals(getAuthenticatedUser())){
            throw new UserIsAuthenticatedUserException("Du kannst dich nicht selbst löschen.");
        }
        List<EquipmentReservation> reservations = new ArrayList<>(equipmentReservationService.getAllByUser(user));
        if(reservations.stream().anyMatch(reservation -> !reservation.isDeletable())){
            throw new ReservationInProgressException("Der Benutzer hat derzeit noch Geräte ausgeliehen und kann erst gelöscht werden nachdem er diese zurückgebracht hat.");
        }
        List<EquipmentGroup> equipmentGroups = new ArrayList<>(user.getEquipmentGroups());
        for(EquipmentGroup equipmentGroup: equipmentGroups){
            user.getEquipmentGroups().remove(equipmentGroup);
            user = userRepository.save(user);
        }

        for(EquipmentReservation reservation: reservations){
            equipmentReservationService.deleteReservation(reservation);
        }

        userRepository.delete(user);
        logger.warn("DELETED User: " + user + " (by " + getAuthenticatedUser() + ")");
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }

}
