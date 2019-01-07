package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.EquipmentGroupRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service for accessing and manipulating EquipmentGroups
 */
@Component
@Scope("application")
public class EquipmentGroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    /**
     * Gets all groups by the currently logged in user.
     * @return
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Collection<EquipmentGroup> getOwnGroups(){
        return equipmentGroupRepository.findAll().stream()
            .filter(equipmentGroup -> equipmentGroup.getUser() == getAuthenticatedUser())
            .collect(Collectors.toList());
    }

    /**
     * Saves a new Equipmentgroup and sets createDate/updateDate
     * @param equipmentGroup
     * @return
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public EquipmentGroup saveEquipmentGroup(EquipmentGroup equipmentGroup){
        if(equipmentGroup.isNew()){
            equipmentGroup.setUser(getAuthenticatedUser());
        }
        return equipmentGroupRepository.save(equipmentGroup);
    }

    /**
     * Deletes an EquipmentGroup
     *
     * @param equipmentGroup
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal eq #equipmentGroup.createdBy")
    public void deleteEquipmentGroup(EquipmentGroup equipmentGroup){
        equipmentGroupRepository.delete(equipmentGroup);
    }

    /**
     * Returns all EquipmentGroups
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<EquipmentGroup> getAllEquipmentGroups(){
        return equipmentGroupRepository.findAll();
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
