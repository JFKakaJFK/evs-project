package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.EquipmentManual;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentManualRepository extends AbstractRepository<EquipmentManual, Integer> {
    //@Query("SELECT m FROM EquipmentManual m WHERE m.id = :id")
    EquipmentManual findById(Integer id);
}
