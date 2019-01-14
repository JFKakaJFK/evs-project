package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.EquipmentManual;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentManualRepository extends AbstractRepository<EquipmentManual, Integer> {

    EquipmentManual findById(Integer id);
}
