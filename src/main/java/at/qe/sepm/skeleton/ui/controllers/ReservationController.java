package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class ReservationController {
    protected boolean addedSuccessfully;
    protected Date lendingDate;
    protected Date returnDate;
    protected ScheduleModel scheduleModel;

    protected String msg;

    @Autowired
    private OpeningHoursService openingHoursService;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private UserService userService;

    public boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    public void setAddedSuccessfully(boolean addedSuccessfully) {
        this.addedSuccessfully = addedSuccessfully;
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

    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public Collection<OpeningHours> getOpeningHours()
    {
        return this.openingHoursService.getAllOpeningHours();
    }

    /*
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    public String getTimeFromDate(Date date)
    {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        return localDateFormat.format(date);
    }

    /**
     * Check if selected time is in opening Hours
     * @return true if valid
     */
    public boolean withinOpeningHours() {
        if(openingHoursService.isWithinOpeningHours(this.lendingDate) && openingHoursService.isWithinOpeningHours(this.returnDate)) {
            return true;
        }
        msg = "Reservierung ist nicht während den Önnungszeiten!";
        return false;
    }

    /**
     * check if enddate is after startdate and after current date
     * @return true if dates are valid
     */
    public boolean validateDate() {
        if(lendingDate == null || returnDate == null)
        {
            msg = "Please select lending- and returndate";
            return false;
        }

        Date today = new Date();
        today.getTime();
        if((today.after(this.returnDate) && today.after(this.lendingDate)) || (returnDate.before(lendingDate))) {
            msg = "Ungültiges Datum";
            return false;
        }

        return true;
    }

    /**
     * adds the reservations of an equipment to schedule
     * @param equipment
     */
    public void addReservationsOfEquipmentToSchedule(Equipment equipment)
    {
        Collection<EquipmentReservation> equipmentReservations = equipmentReservationService.getAllEquipmentReservationsContaining(equipment);
        for(EquipmentReservation equipmentReservation : equipmentReservations)
        {
            String duration = getTimeFromDate(equipmentReservation.getStartDate()) + " - " + getTimeFromDate(equipmentReservation.getEndDateOverdue());
            scheduleModel.addEvent(new DefaultScheduleEvent(duration, equipmentReservation.getStartDate(), equipmentReservation.getEndDateOverdue()));
        }
    }

    /**
     * add an reservation of given equipment
     * @param equipment
     */
    public void addEquipmentToReservations(Equipment equipment)
    {
        EquipmentReservation equipmentReservation = new EquipmentReservation();
        equipmentReservation.setStartDate(this.lendingDate);
        equipmentReservation.setEndDate(this.returnDate);
        equipmentReservation.setEquipment(equipment);
        equipmentReservation.setUser(userService.getAuthenticatedUser());

        this.equipmentReservationService.saveReservation(equipmentReservation);
    }

    /**
     * Check URL in order to know if reservations was added successfully.
     * In that case a growl success message will be displayed.
     */
    public void checkURL() {
        Iterator<String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterNames();
        if(params.hasNext()) {
            String parameter = params.next();
            if(parameter.equals("addedSuccessfully")) {
                this.addedSuccessfully = true;
            }
        }
    }
}
