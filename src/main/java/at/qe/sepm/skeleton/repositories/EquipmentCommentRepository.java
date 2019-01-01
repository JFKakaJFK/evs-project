package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.model.User;

import java.util.Collection;

public interface EquipmentCommentRepository extends AbstractRepository<EquipmentComment, Integer> {
    Collection<EquipmentComment> findAllByCreateUser(User createUser);
}
