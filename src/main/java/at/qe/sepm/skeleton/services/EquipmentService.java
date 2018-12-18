package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope("application")
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    // @PreAuthorize("hasAuthority('STUDENT')")

    /* TODO

     * STUDENT
     *
     * getAllFreeEquipments(start, end)
     * getEquipmentsByName(name)

     * ADMIN
     *
     * crudEquipment
     */

}
