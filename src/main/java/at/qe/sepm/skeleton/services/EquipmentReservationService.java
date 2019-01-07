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
import java.util.Date;
import java.util.stream.Collectors;

public class EquipmentReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    /**
     * Saves a manual
     *
     * @param manual
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public EquipmentReservation saveManual(EquipmentReservation manual){
        if(manual.isNew()){
            manual.setCreateDate(new Date());
        }
        return equipmentReservationRepository.save(manual);
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
