package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.RelationService;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchedulerBean {
    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private UserService userService;


    //execute all 5mins
    @Scheduled(fixedRate = 50000)
    public void checkReservations()
    {
        List<User> users = new ArrayList<>();
        users.addAll(userService.getAllUsers());

        if(users.size() > 0)
        {
            for(User user : users)
            {
                sendEmailsBeforeOverdue(
                    (List<EquipmentReservation>) equipmentReservationService.getAllLongReservationsEndingSoonByUser(user)
                );
                sendEmailsOverdue(
                    (List<EquipmentReservation>) equipmentReservationService.getAllOverdueReservations(user)
                );
            }


        }
    }


    public void sendEmailsOverdue(List<EquipmentReservation> reservations)
    {
        if(reservations != null)
        {

        }
    }

    public void sendEmailsBeforeOverdue(List<EquipmentReservation> reservations)
    {

    }
}
