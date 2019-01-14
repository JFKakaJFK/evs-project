package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.model.User;

import java.util.Collection;

public interface EquipmentGroupRepository extends AbstractRepository<EquipmentGroup, Integer> {

    public Collection<EquipmentGroup> findAllByUser(User user);

    public Collection<EquipmentGroup> findAllByEquipmentsContains(Equipment equipment);
}
