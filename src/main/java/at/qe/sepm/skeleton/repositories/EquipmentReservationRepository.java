package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;

import java.util.List;

public interface EquipmentReservationRepository extends AbstractRepository<EquipmentReservation, Integer> {

    List<EquipmentReservation> findAllByUser(User user);
}
