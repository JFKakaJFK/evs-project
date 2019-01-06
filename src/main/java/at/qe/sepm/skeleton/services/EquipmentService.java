package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentComment;
import at.qe.sepm.skeleton.model.EquipmentManual;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.EquipmentCommentRepository;
import at.qe.sepm.skeleton.repositories.EquipmentManualRepository;
import at.qe.sepm.skeleton.repositories.EquipmentRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
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

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @PreAuthorize("hasAuthority('ADMIN')")
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
     * Returns a collection of all free eqipments in a timeframe
     *
     * @param startDate of timeframe
     * @param endDate of timeframe
     * @return
     */
    @PreAuthorize("hasAuthority('STUDENT')")
    public Collection<Equipment> getAllFreeEquipments(Date startDate, Date endDate){
        return equipmentRepository.findAll().stream()
            .filter(equipment -> equipment.isAvailable(startDate, endDate))
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
            equipment.setCreateUser(getAuthenticatedUser());
        }
        return equipmentRepository.save(equipment);
    }

    /**
     * Deletes the Equipment and logs to logfile.
     *
     * @param equipment to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEquipment(Equipment equipment){
        equipmentRepository.delete(equipment);
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
