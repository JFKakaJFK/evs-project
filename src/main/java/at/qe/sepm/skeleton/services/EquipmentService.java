package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for accessing and manipulating equipment related data.
 */
@Component
@Scope("application")
public class EquipmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentCommentRepository equipmentCommentRepository;

    @Autowired
    private EquipmentManualRepository equipmentManualRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    public Equipment loadEquipment(Integer id){
        return equipmentRepository.findById(id);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentComment loadComment(Integer id){
        return equipmentCommentRepository.findById(id);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentManual loadManual(Integer id){
        return equipmentManualRepository.findById(id);
    }

    /**
     * Returns a collectioin of all Equipments which need to be returned or are overdue
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Equipment> getAllBorrowedEquipments(){
        Collection<Equipment> equipments = equipmentRepository.findAll();
        return equipments.stream()
            .filter(equipment -> (equipment.getState() == EquipmentState.BOOKED || equipment.getState() == EquipmentState.OVERDUE))
            .collect(Collectors.toList());
    }


    /**
     * returns a collection of all equipments whose maximal reservation duration is within a timeframe
     *
     * @param startDate of timeframe
     * @param endDate of timeframe
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllWithinMaxDuration(Date startDate, Date endDate){
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.isWithinMaxReservationDuration(startDate, endDate))
            .collect(Collectors.toList());
    }

    /**
     * Returns a collection of all free eqipments in a timeframe
     *
     * @param startDate of timeframe
     * @param endDate of timeframe
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllFreeEquipments(Date startDate, Date endDate){
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.getState(startDate, endDate) == EquipmentState.AVAILABLE ||
                (equipment.getState(startDate, endDate) == EquipmentState.OVERDUE &&
                    (equipment.getOverdueReservation() == null) ? (false) : (startDate.getTime() > equipment.getOverdueReservation().getEndDateOverdue().getTime())))
            .collect(Collectors.toList());
    }

    /**
     * Get all equipments by name
     *
     * @param name name which is sought for
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getEquipmentsByName(String name){
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Saves the equipment. This method will also set {@link Equipment#createDate} for new
     * entities. The user
     * requesting this operation will also be stored as {@link Equipment#createDate}.
     *
     * @param equipment Equipment to save
     * @return the updated Equipment
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Equipment saveEquipment(Equipment equipment){
        if(equipment.isNew()){
            equipment.setCreateDate(new Date());
        }
        return equipmentRepository.save(equipment);
    }

    /**
     * Deletes all Equipments from an Equipment Group
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    private void deleteAllEquipmentsFromGroup(EquipmentGroup equipmentGroup){
        List<Equipment> equipments = new ArrayList<>(equipmentGroup.getEquipments());
        // Detach ManyToMany(Equipment)
        for (Equipment e: equipments) {
            e.removeEquipmentGroup(equipmentGroup);
        }
        // Remove Group
        equipmentGroupRepository.save(equipmentGroup);
    }


    /**
     * Deletes all Groups from an Equipment
     *
     * @param equipment
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    private void deleteAllGroupsFromEquipment(Equipment equipment){
        List<EquipmentGroup> equipmentGroups = new ArrayList<>(equipment.getEquipmentGroups());
        // Detach ManyToMany(Equipment)
        for (EquipmentGroup group: equipmentGroups) {
            if(group.getEquipments().size() < 3){
                deleteAllEquipmentsFromGroup(group);
                User u = group.getUser();
                u.removeEquipmentGroup(group);
                userRepository.save(u);
            } else {
                group.getEquipments().remove(equipment);
                equipment.removeEquipmentGroup(group);
            }
        }
        // Remove Group
        equipmentRepository.save(equipment);
    }

    /**
     * Deletes the Equipment and logs to logfile.
     *
     * @param equipment to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEquipment(Equipment equipment){
        deleteAllGroupsFromEquipment(equipment);
        System.out.println("test");
        equipmentRepository.delete(equipment);
        System.out.println("test");
        logger.warn("DELETED Equipment: " + equipment.getName() + " (by " + getAuthenticatedUser().getEmail() + ")");
    }

    /**
     * Saves a comment
     *
     * @param comment
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public EquipmentComment saveComment(EquipmentComment comment){
        if(comment.isNew()){
            comment.setCreateDate(new Date());
            comment.setCreateUser(getAuthenticatedUser());
        }
        return equipmentCommentRepository.save(comment);
    }

    // TODO move deletion method here(from equipmentDetailController)
    /**
     * Deletes a comment
     *
     * @param comment
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteComment(EquipmentComment comment){
        equipmentCommentRepository.delete(comment);
    }

    /**
     * Saves a manual
     *
     * @param manual
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public EquipmentManual saveManual(EquipmentManual manual){
        if(manual.isNew()){
            manual.setCreateDate(new Date());
            manual.setCreateUser(getAuthenticatedUser());
        }
        return equipmentManualRepository.save(manual);
    }

    // TODO move deletion method here(from equipmentDetailController)
    /**
     * Deletes a manual
     *
     * @param manual
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteManual(EquipmentManual manual){
        equipmentManualRepository.delete(manual);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
