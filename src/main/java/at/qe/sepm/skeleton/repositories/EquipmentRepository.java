package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Equipment;

import java.util.List;

/**
 * Repository for managing {@link Equipment} entities.
 */
public interface EquipmentRepository extends AbstractRepository<Equipment, Integer> {

    Equipment findById(Integer id);
}
