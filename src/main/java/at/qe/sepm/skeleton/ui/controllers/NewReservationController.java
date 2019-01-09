package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope("view")
public class NewReservationController implements Serializable {
    private Date lendingDate;
    private Date returnDate;

    private List<Equipment> selectedEquipments;
    private List<Equipment> filteredEquipments;
    private Collection<Equipment> defaultEquipments;

    private ScheduleModel scheduleModel;

    private Equipment detailEquipment;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private OpeningHoursService openingHoursService;

    @PostConstruct
    public void Init()
    {
        scheduleModel = new DefaultScheduleModel();
        defaultEquipments = equipmentService.getAllEquipments();
    }

    public Collection<Equipment> getDefaultEquipments() {
        return defaultEquipments;
    }

    public void setDefaultEquipments(Collection<Equipment> defaultEquipments) {
        this.defaultEquipments = defaultEquipments;
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

    public List<Equipment> getSelectedEquipments() {
        return selectedEquipments;
    }

    public void setSelectedEquipments(List<Equipment> selectedEquipments) {
        this.selectedEquipments = selectedEquipments;
    }

    public List<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(List<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public ScheduleModel getScheduleModel()
    {
        return this.scheduleModel;
    }

    public Equipment getDetailEquipment() {
        return detailEquipment;
    }

    public void setDetailEquipment(Equipment detailEquipment) {
        this.detailEquipment = detailEquipment;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public String calendarAction()
    {
        scheduleModel.clear();

        int equipmentID = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("detailEquipment"));
        detailEquipment = equipmentService.loadEquipment(equipmentID);

        //update calender infos
        Collection<EquipmentReservation> equipmentReservations = equipmentReservationService.getAllEquipmentReservationsContaining(detailEquipment);
        for(EquipmentReservation equipmentReservation : equipmentReservations)
        {
            String duration = getTimeFromDate(equipmentReservation.getStartDate()) + " - " + getTimeFromDate(equipmentReservation.getEndDate());
            scheduleModel.addEvent(new DefaultScheduleEvent(duration, equipmentReservation.getStartDate(), equipmentReservation.getEndDate()));
        }

        return null;
    }

    public void dateChanged(SelectEvent event)
    {
        if(this.lendingDate != null && this.returnDate != null)
        {
            if(returnDate.after(lendingDate) || lendingDate.equals(returnDate))
            {
                Collection<Equipment> freeEquipments = equipmentService.getAllFreeEquipments(lendingDate, returnDate);

                if(filteredEquipments == null)
                {
                    this.defaultEquipments = freeEquipments;
                }

                else
                {
                    List<Equipment> toRemove = new ArrayList<>();
                    toRemove.addAll(filteredEquipments);

                    toRemove.removeAll(freeEquipments);
                    filteredEquipments.removeAll(toRemove);
                    defaultEquipments.clear();
                    defaultEquipments.addAll(filteredEquipments);
                }
            }
        }
    }

    public Collection<OpeningHours> getOpeningHours()
    {
        return this.openingHoursService.getAllOpeningHours();
    }


    public void resetInputs()
    {
        this.defaultEquipments = equipmentService.getAllEquipments();
        this.lendingDate = null;
        this.returnDate = null;

        PrimeFaces.current().executeScript("PF('equipmentSelect').clearFilters()");
        PrimeFaces.current().resetInputs("newReservation:newReservationPanel");
    }

    public String getTimeFromDate(Date date)
    {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        return localDateFormat.format(date);
    }
}
