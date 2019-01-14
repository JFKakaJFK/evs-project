package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EquipmentCommentRepository extends AbstractRepository<EquipmentComment, Integer> {

    EquipmentComment findById(Integer id);
}
