package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.exceptions.EquipmentDeletionException;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserRepository userRepository;

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

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

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
     * Loads a single {@link EquipmentManual} by its {@link EquipmentManual#id}
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public EquipmentManual loadManual(Integer id){
        return equipmentManualRepository.findById(id);
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
        /*
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.getState(startDate, endDate) == EquipmentState.AVAILABLE ||
                (equipment.getState(startDate, endDate) == EquipmentState.OVERDUE &&
                    (equipment.getOverdueReservation() == null) ? (false) : (startDate.after(equipment.getOverdueReservation().getEndDateOverdue()))))
            .collect(Collectors.toList());
            */
        // TODO LOGIC
        List<Equipment> all = equipmentRepository.findAll();
        List<Equipment> free = new ArrayList<>();
        for(Equipment e: all){
            if((e.getState(startDate, endDate) == EquipmentState.AVAILABLE)){
                free.add(e);
            }
            EquipmentReservation r = e.getOverdueReservation();
            if(r != null){
                if(r.getEndDateOverdue().before(startDate)){
                    free.add(e);
                }
            }
        }
        return free;
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
        // Detach ManyToMany(Equipment)
        for (EquipmentGroup group: equipmentGroups) {
            // delete group if there are too few equipments left
            if(group.getEquipments().size() < 3){
                User u = group.getUser();
                u.removeEquipmentGroup(group);
                userRepository.save(u);
                // TODO: log group deletion
            } else {
                group.getEquipments().remove(equipment);
                userRepository.save(group.getUser());
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
            logger.warn("FAILED to delete manual on delete equipment action");
        } finally {
            equipment = deleteAllGroupsFromEquipment(equipment);
            equipmentRepository.delete(equipment);

            logger.warn("DELETED Equipment: " + equipment.getName() + " (by " + getAuthenticatedUser().getEmail() + ")");
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

    /**
     * Returns the currently authenticated User
     *
     * @return
     */
    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
