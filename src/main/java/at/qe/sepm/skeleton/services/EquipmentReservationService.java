package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.EquipmentState;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.EquipmentReservationRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Service for accessing and manipulating equipment related data.
 */
@Component
@Scope("application")
public class EquipmentReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private EquipmentService equipmentService;

    /**
     * Retuns all Reservations where the equipment state is BOOKED
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<EquipmentReservation> getAllBorrowedEquipments(){
        Collection<Equipment> borrowed = equipmentService.getAllBorrowedEquipments();
        return equipmentReservationRepository.findAll().stream()
            .filter(reservation -> borrowed.contains(reservation.getEquipment()))
            .collect(Collectors.toList());
    }

    /**
     * Loads a reservation by its id
     *
     * @param id
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentReservation loadRerservation(Integer id){
        return equipmentReservationRepository.findOne(id);
    }

    /**
     * Returns all reservations containing a specific equipment
     *
     * @param equipment
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<EquipmentReservation> getAllEquipmentReservationsContaining(Equipment equipment){
        return equipmentReservationRepository.findAllByEquipment(equipment);
    }

    /**
     * Returns all reservations
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<EquipmentReservation> getAllEquipmentReservations(){
        return equipmentReservationRepository.findAll();
    }

    /**
     * Returns all Reservations by user
     *
     * @param user
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<EquipmentReservation> getAllByUser(User user){
        return equipmentReservationRepository.findAllByUser(user);
    }

    /**
     * Saves a reservation
     *
     * @param reservation
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public EquipmentReservation saveReservation(EquipmentReservation reservation){
        if(reservation.isNew()){
            reservation.setCreateDate(new Date());
        }
        return equipmentReservationRepository.save(reservation);
    }

    /**
     * Deletes a reservation without checking whether the start Date is in the Future
     *
     * @param reservation
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal eq #reservation.getUser()")
    public void deleteReservation(EquipmentReservation reservation){
        Equipment e = reservation.getEquipment();
        e.removeReservation(reservation);
        equipmentService.saveEquipment(e);

        // TODO log reservation deletet by whom
    }

    /**
     * Returns the currently authenticated User
     *
     * @return
     */
    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
