package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.repositories.EquipmentGroupRepository;
import at.qe.sepm.skeleton.repositories.EquipmentRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class EquipmentGroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    //TODO add authorization (admin or user is creator)

    /*
    * TODO
    *
    * addEquipmentGroup(Set<Equipment>, name) user = getAuthenticatedUser()
    * deleteEquipmentGroup(EquipmentGroup)
    * ...
    */

}
