package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;

import java.util.List;

/**
 * Repository for managing {@link EquipmentReservation} entities.
 */
public interface EquipmentReservationRepository extends AbstractRepository<EquipmentReservation, Integer> {

    List<EquipmentReservation> findAllByUser(User user);

    List<EquipmentReservation> findAllByEquipment(Equipment equipment);

    List<EquipmentReservation> findAllByCompleted(Boolean completed);
}
