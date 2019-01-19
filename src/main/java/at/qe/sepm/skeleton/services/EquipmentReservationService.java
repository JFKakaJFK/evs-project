package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.exceptions.ReservationInProgressException;
import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.EquipmentRepository;
import at.qe.sepm.skeleton.repositories.EquipmentReservationRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.primefaces.util.DateUtils;
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
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * Retuns all Reservations where the equipment state is BOOKED
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<EquipmentReservation> getAllBorrowedEquipments(){
        return equipmentReservationRepository.findAll().stream()
            .filter(reservation -> !reservation.isCompleted() && reservation.getStartDate().before(new Date()))
            .collect(Collectors.toList());
    }

    /**
     * Returns all overdue reservations bz the user
     *
     * @param user
     */
    public Collection<EquipmentReservation> getAllOverdueReservations(User user){
        return equipmentReservationRepository.findAllByCompletedAndUser(false, user).stream()
            .filter(reservation -> reservation.getEndDate().before(new Date()))
            .collect(Collectors.toList());
    }

    /**
     * Returns all reservations from {@link User} which are booked for more than 3 days and should end within 24 hours
     *
     * @param user
     */
    public Collection<EquipmentReservation> getAllLongReservationsEndingSoonByUser(User user){
        long day = 24 * 60 * 60 * 1000;
        return equipmentReservationRepository.findAllByCompletedAndUser(false, user).stream()
            .filter(reservation -> !reservation.isOverdue()
                && (reservation.getEndDate().getTime() - reservation.getStartDate().getTime()) > 3 * day
                && (reservation.getEndDate().getTime() - new Date().getTime() < day))
            .collect(Collectors.toList());
    }

    /**
     * Loads a reservation by its id
     *
     * @param id
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentReservation loadReservation(Integer id){
        return equipmentReservationRepository.findOne(id);
    }

    /**
     * Returns all reservations containing a specific equipment
     *
     * @param equipment
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<EquipmentReservation> getAllEquipmentReservationsContaining(Equipment equipment){
        return equipmentReservationRepository.findAllByEquipment(equipment);
    }

    /**
     * Returns all reservations
     *
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
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
    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentReservation saveReservation(EquipmentReservation reservation){
        if(reservation.isNew()){
            reservation.setCreateDate(new Date());
        }
        reservation.getEquipment().addReservation(reservation);
        return equipmentReservationRepository.save(reservation);
    }

    /**
     * Deletes a reservation without checking whether the start Date is in the Future
     *
     * @param reservation
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #reservation.getUser().username")
    public void deleteReservation(EquipmentReservation reservation) throws ReservationInProgressException {
        if(reservation.isDeletable()){
            Equipment e = reservation.getEquipment();
            e.removeReservation(reservation);
            equipmentRepository.save(e);
        } else {
            throw new ReservationInProgressException("Der Benutzer hat derzeit noch Geräte ausgeliehen und kann erst gelöscht werden nachdem er diese zurückgebracht hat.");
        }
        // TODO log reservation deleted by whom
    }
}
