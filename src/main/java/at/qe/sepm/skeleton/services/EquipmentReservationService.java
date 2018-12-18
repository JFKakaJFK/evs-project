package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.EquipmentReservationRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.stream.Collectors;

public class EquipmentReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    /* TODO

     * STUDENT
     *
     * getOwnReservations()
     * saveReservation(reservation) throws NOTAVAILABLE

     * EMPLOYEE
     *
     * getOwnGroups()
     * getOwnGroupReservations()
     * addGroup(<Equipment> >2, name)
     * addGroupReservation(Group, start, end) throws NOTAVAILABLE
     *

     * ADMIN
     *
     * removeReservation(Reservation) OR user = creator
     * removeGroup(Group) OR user = creator
     *
     * getAllGroups()
     * getAllGroupReservations()
     * getAllReservations()
     *
     * confirmReturn(Reservation)
     */

    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<EquipmentReservation> getOwnReservations(){
        return equipmentReservationRepository.findAllByUser(getAuthenticatedUser()).stream()
            .filter(reservation -> reservation.getGroupName() == null)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentReservation saveReservation(EquipmentReservation reservation){
        if(reservation.isNew()){
            //TODO do something?
        }
        return equipmentReservationRepository.save(reservation);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Collection<EquipmentReservation> getOwnGroupReservations(){
        return equipmentReservationRepository.findAllByUser(getAuthenticatedUser()).stream()
            .filter(reservation -> reservation.getGroupName() != null)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal eq #reservation.getUser()")
    public void deleteReservation(EquipmentReservation reservation){
        equipmentReservationRepository.delete(reservation);
        // TODO log reservation deletet by whom
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
