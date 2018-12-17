package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    //TODO: add authorization when implemented


    //TODO: addReservation(equipment, user, start, end) ->  end - start < maxDuration
    //TODO: deleteReservation(equipment, user, start, end)

}
