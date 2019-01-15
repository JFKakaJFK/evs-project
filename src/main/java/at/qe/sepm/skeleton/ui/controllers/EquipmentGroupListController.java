package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import at.qe.sepm.skeleton.services.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Controller for providing collections of {@link EquipmentGroup}s
 */
@Component
@Scope("view")
public class EquipmentGroupListController {

    @Autowired
    private EquipmentGroupService groupService;

    /**
     * Returns a list of all groups.
     *
     * @return
     */
    public Collection<EquipmentGroup> getAllEquipmentGroups() {
        return groupService.getAllEquipmentGroups();
    }

    /**
     * Returns a list of all groups owned by the current user.
     *
     * @return
     */
    public Collection<EquipmentGroup> getAllEquipmentGroupsByUser(){
        return groupService.getOwnGroups();
    }

}
