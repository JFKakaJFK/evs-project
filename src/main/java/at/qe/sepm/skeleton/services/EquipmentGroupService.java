package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Equipment;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    /**
     * Gets all groups by the currently logged in user.
     * @return
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Collection<EquipmentGroup> getOwnGroups(){
        return equipmentGroupRepository.findAllByUser(getAuthenticatedUser());
    }

    /**
     * Loads and returns the EquipmentGroup by its Id
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public EquipmentGroup loadEquipmentGroup(Integer id){
        return equipmentGroupRepository.findOne(id);
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
            getAuthenticatedUser().addEquipmentGroup(equipmentGroup);
        }
        return equipmentGroupRepository.save(equipmentGroup);
    }

    /**
     * Deletes an EquipmentGroup
     *
     * @param equipmentGroup
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #equipmentGroup.user.username")
    public void deleteEquipmentGroup(EquipmentGroup equipmentGroup){
        List<Equipment> equipments = new ArrayList<>(equipmentGroup.getEquipments());
        User u = equipmentGroup.getUser();
        u.getEquipmentGroups().remove(equipmentGroup);
        for (Equipment e: equipments) {
            e.removeEquipmentGroup(equipmentGroup);
            // equipmentService.saveEquipment(e);
        }

        equipmentGroupRepository.delete(equipmentGroup);

        userRepository.save(u);
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