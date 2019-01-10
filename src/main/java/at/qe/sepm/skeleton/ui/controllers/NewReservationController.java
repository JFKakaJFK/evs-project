package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
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
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope("view")
public class NewReservationController implements Serializable {
    private boolean addedSuccessfully;

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

    @Autowired
    private UserService userService;

    @PostConstruct
    public void Init()
    {
        scheduleModel = new DefaultScheduleModel();
        defaultEquipments = equipmentService.getAllEquipments();
        addedSuccessfully = false;
    }

    public boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    public void setAddedSuccessfully(boolean addedSuccessfully) {
        this.addedSuccessfully = addedSuccessfully;
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
            //ToDo: check max duration

            if(returnDate.after(lendingDate) || lendingDate.equals(returnDate))
            {
                Collection<Equipment> freeEquipments = null;
                if(withinOpeningHours())
                {
                    freeEquipments = equipmentService.getAllFreeEquipments(lendingDate, returnDate);
                }

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

    /**
     * Resets all Inputs in View
     */
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

    /*
        Code von Melanie :)
     */
    public void addEquipmentReservation() throws IOException {
        //Todo: error and success information
    	//ToDo: check max duration for each equipment
        //check if equipments are avialbe and validate Date
        if(equipmentsAvailabe() && validateDate() && withinOpeningHours())
        {

            for(Equipment newEquipment : this.selectedEquipments)
            {
                EquipmentReservation equipmentReservation = new EquipmentReservation();
                equipmentReservation.setStartDate(this.lendingDate);
                equipmentReservation.setEndDate(this.returnDate);
                equipmentReservation.setEquipment(newEquipment);
                equipmentReservation.setUser(userService.getAuthenticatedUser());

                this.equipmentReservationService.saveReservation(equipmentReservation);
            }
            String msg;
            msg = "Reservation(s) added successfully";
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml?addedSuccessfully");
        }
    }

    /**
     * check if the equipment is available
     * @return
     */
	public boolean isAvailable() {
		Collection<EquipmentReservation> allEquipmentReservations = new ArrayList<EquipmentReservation>();
		for (Equipment equipment : selectedEquipments) {
			allEquipmentReservations = equipmentReservationService.getAllEquipmentReservationsContaining(equipment);
			for (EquipmentReservation er : allEquipmentReservations) {
				if (!((this.getLendingDate().after(er.getEndDate()))
						|| (this.getReturnDate().before(er.getStartDate())))) {
					return false;
				}
				if(!(er.getEquipment().isWithinMaxReservationDuration(this.getLendingDate(), this.getReturnDate()))) {
					return false;
				}
			}

		}
		return true;
	}

    /**
     * Check if selected time is in opening Hours
     * @return true if valid
     */
    public boolean withinOpeningHours() {
    	return this.openingHoursService.isWithinOpeningHours(this.lendingDate) && openingHoursService.isWithinOpeningHours(this.returnDate);
    }
    
    /**
     * check if enddate is after startdate and after current date
     * @return true if dates are valid
     */
    public boolean validateDate() {
        Date today = new Date();
        today.getTime();
        if(today.after(this.returnDate) && today.after(this.lendingDate)) {
            return false;
        }
        if(returnDate.before(lendingDate)) {
            return false;
        }

        return true;
    }

    /**
     * checks if selected equipments are avialable
     */
    public boolean equipmentsAvailabe()
    {
        if(selectedEquipments == null || lendingDate == null || returnDate == null)
            return false;

        //check if equipments are avialbe
        List<Equipment> freeEquipments = (List<Equipment>) equipmentService.getAllFreeEquipments(lendingDate, returnDate);

        for(Equipment equipment : this.selectedEquipments)
        {
            if(!freeEquipments.contains(equipment))
                return false;
        }

        return true;
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
