package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.exceptions.EquipmentDeletionException;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for accessing and manipulating {@link Equipment}, {@link EquipmentManual} and {@link EquipmentComment} entities.
 */
@Component
@Scope("application")
public class EquipmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentCommentRepository equipmentCommentRepository;

    @Autowired
    private EquipmentManualRepository equipmentManualRepository;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    @Autowired
    private StorageService storageService;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EquipmentService.class);

    /**
     * Method retrieves and returns all Equipments
     *
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    /**
     * Method retrieves and returns a single Equipment
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Equipment loadEquipment(Integer id){
        return equipmentRepository.findById(id);
    }

    /**
     * Loads a single {@link EquipmentComment} by its {@link EquipmentComment@id}
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentComment loadComment(Integer id){
        return equipmentCommentRepository.findById(id);
    }

    /**
     * Returns a collectioin of all Equipments which are currently booked or are overdue
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Equipment> getAllBorrowedEquipments(){
        Collection<Equipment> equipments = equipmentRepository.findAll();
        return equipments.stream()
            .filter(equipment -> (equipment.getState() == EquipmentState.BOOKED || equipment.getState() == EquipmentState.OVERDUE))
            .collect(Collectors.toList());
    }

    /**
     * Returns a collection of all free {@link Equipment} entities in a time frame
     *
     * @param startDate of timeframe
     * @param endDate of timeframe
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllFreeEquipments(Date startDate, Date endDate){
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.getState(startDate, endDate) == EquipmentState.AVAILABLE)
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
     * Deletes all Groups from an Equipment
     *
     * @param equipment
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    private Equipment deleteAllGroupsFromEquipment(Equipment equipment){
        List<EquipmentGroup> equipmentGroups = new ArrayList<>(equipmentGroupRepository.findAllByEquipmentsContains(equipment));
        List<EquipmentGroup> delete = new ArrayList<>();
        // Detach ManyToMany(Equipment)
        for (EquipmentGroup group: equipmentGroups) {
            // delete group if there are too few equipments left

            if(group.getEquipments().size() < 3){
                group.getEquipments().clear();
                User u = userService.loadUser(group.getUser().getUsername());
                u.getEquipmentGroups().remove(group);
                if(u.getEquipmentGroups().contains(group)){
                    System.out.println("WTFFFF");
                    u.getEquipmentGroups().remove(group);
                }
                //group.setUser(null);
                userService.saveUser(u);

                logger.warn("DELETED Group: " + group + " (by " + userService.getAuthenticatedUser().getEmail() + ")");
            } else {
                group.getEquipments().remove(equipment);
                userService.saveUser(group.getUser());
                logger.warn("DELETED Equipment " + equipment + " from group " + group + " (by " + userService.getAuthenticatedUser().getEmail() + ")");
            }
        }
        return equipmentRepository.save(equipment);
    }

    /**
     * Deletes the Equipment and logs to logfile.
     *
     * @param equipment to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEquipment(Equipment equipment) throws EquipmentDeletionException {
        if(equipment.getState() == EquipmentState.BOOKED || equipment.getState() == EquipmentState.OVERDUE){
            throw new EquipmentDeletionException("Ein ausgeliehenes Gerät kann nicht gelöscht werden.");
        }
        // if it can be deleted, try to delete all its manuals
        try {
            for (EquipmentManual manual : equipment.getManuals()) {
                storageService.deleteFile(manual.getFilename());
            }
        } catch (IOException e){
            logger.warn("FAILED to delete manual on delete equipment " + equipment.getId() + " [" + equipment.getName() + "] action");
        } finally {
            equipment = deleteAllGroupsFromEquipment(equipment);
            equipmentRepository.delete(equipment);

            logger.warn("DELETED Equipment: " + equipment.getName() + " (by " + userService.getAuthenticatedUser().getEmail() + ")");
        }
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
        }
        comment.getEquipment().addComment(comment);
        return equipmentCommentRepository.save(comment);
    }

    /**
     * Deletes a comment
     *
     * @param comment
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteComment(EquipmentComment comment){
        Equipment e = comment.getEquipment();
        e.removeComment(comment);
        equipmentRepository.save(e);
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
        }
        manual.getEquipment().addManual(manual);
        return equipmentManualRepository.save(manual);
    }

    /**
     * Deletes a manual
     *
     * @param manual
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteManual(EquipmentManual manual){
        try{
            storageService.deleteFile(manual.getFilename());
        } catch (IOException e){
            // TODO log file wasn't found/couldn't be deleted
        }
        Equipment e = manual.getEquipment();
        e.removeManual(manual);
        equipmentRepository.save(e);
    }
}
