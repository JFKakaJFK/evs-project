package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;

import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;

import java.util.Date;


@Controller
@Scope("view")
public class DashboardController {

    private SessionRegistry sessionRegistry;
    private UserService userService;
    private OpeningHoursService openingHoursService;
    private EquipmentReservationService equipmentReservationService;
    private SessionInfoBean sessionInfoBean;

    @Autowired
    public DashboardController(SessionRegistry sessionRegistry,
                               UserService userService,
                               OpeningHoursService openingHoursService,
                               EquipmentReservationService equipmentReservationService,
                               SessionInfoBean sessionInfoBean){
        this.sessionRegistry = sessionRegistry;
        this.userService = userService;
        this.openingHoursService = openingHoursService;
        this.equipmentReservationService = equipmentReservationService;
        this.sessionInfoBean = sessionInfoBean;
    }

    public int getNumLoggedInUsers(){
        return sessionRegistry.getAllPrincipals().size();
    }

    public int getNumUsers(){
        return userService.getAllUsers().size();
    }

    public boolean isOpen(){
        return openingHoursService.isWithinOpeningHours(new Date());
    }

    public String getWeekday(){
        return openingHoursService.getWeekDay(new Date());
    }

    public long reservationsByUserAndState(String state){
        return equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).stream()
            .filter(reservation -> reservation.getState().equals(state))
            .count();
    }

    public long reservationsByState(String state){
        return equipmentReservationService.getAllEquipmentReservations().stream()
            .filter(reservation -> reservation.getState().equals(state))
            .count();
    }

    public double getTotalOverduePercent(){
        return (((double) equipmentReservationService.getAllEquipmentReservations().stream()
            .filter(EquipmentReservation::isOverdue)
            .count()) / equipmentReservationService.getAllEquipmentReservations().size()) * 100;
    }

    public double getTotalActivePercent(){
        return (((double) equipmentReservationService.getAllEquipmentReservations().stream()
            .filter(reservation -> !reservation.isCompleted() && reservation.getStartDate().before(new Date()))
            .count()) / equipmentReservationService.getAllEquipmentReservations().size()) * 100;
    }

    public double getTotalDonePercent(){
        return (((double) equipmentReservationService.getAllEquipmentReservations().stream()
                .filter(reservation -> !reservation.isCompleted())
                .count())
                / equipmentReservationService.getAllEquipmentReservations().size()) * 100;
    }

    public double getTotalOverduePercentUser(){
        return (((double) equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).stream()
            .filter(EquipmentReservation::isOverdue)
            .count()) / equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).size()) * 100;
    }

    public double getTotalActivePercentUser(){
        return (((double) equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).stream()
            .filter(reservation -> !reservation.isCompleted() && reservation.getStartDate().before(new Date()))
            .count()) / equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).size()) * 100;
    }

    public double getTotalDonePercentUser(){
        return (((double) equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).stream()
            .filter(reservation -> !reservation.isCompleted())
            .count())
            / equipmentReservationService.getAllByUser(sessionInfoBean.getCurrentUser()).size()) * 100;
    }
}
