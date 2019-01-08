package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Component
@Scope("view")
public class NewReservationController implements Serializable {
    private Date lendingDate;
    private Date returnDate;

    private Collection<Equipment> selectedEquipments;
    private Collection<Equipment> filteredEquipments;

    private ScheduleModel scheduleModel;

    @Autowired
    private EquipmentService equipmentService;

    public NewReservationController() {
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Collection<Equipment> getAllEquipments()
    {
        return equipmentService.getAllEquipments();
    }

    public Collection<Equipment> getSelectedEquipments() {
        return selectedEquipments;
    }

    public void setSelectedEquipments(Collection<Equipment> selectedEquipments) {
        this.selectedEquipments = selectedEquipments;
    }

    public Collection<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(Collection<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public void Calendar(Equipment equipment)
    {
        equipmentService.getAllEquipments();
    }
}
