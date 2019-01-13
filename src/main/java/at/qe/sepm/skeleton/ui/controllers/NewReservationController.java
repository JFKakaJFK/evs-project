package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.OpeningHoursService;
import at.qe.sepm.skeleton.services.UserService;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.primefaces.context.RequestContext;
import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Component
@Scope("view")
public class NewReservationController extends ReservationController implements Serializable {
    private boolean addedSuccessfully;

    private List<Equipment> selectedEquipments;
    private List<Equipment> filteredEquipments;
    private Collection<Equipment> defaultEquipments;

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

    public Collection<Equipment> getDefaultEquipments() {
        return defaultEquipments;
    }

    public void setDefaultEquipments(Collection<Equipment> defaultEquipments) {
        this.defaultEquipments = defaultEquipments;
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

    public Equipment getDetailEquipment() {
        return detailEquipment;
    }

    public void setDetailEquipment(Equipment detailEquipment) {
        this.detailEquipment = detailEquipment;
    }

    public String calendarAction()
    {
        scheduleModel.clear();

        int equipmentID = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("detailEquipment"));
        detailEquipment = equipmentService.loadEquipment(equipmentID);

        //update calender infos
        addReservationsOfEquipmentToSchedule(detailEquipment);

        return null;
    }

    public void dateChanged(SelectEvent event)
    {
        if(this.lendingDate != null && this.returnDate != null)
        {
            //ToDo: check if selcted time + weekday is in openinghours
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


    /**
     *
     * @throws IOException
     */
    public void addEquipmentReservation() throws IOException {
        //Todo: error and success information
    	//ToDo: check max duration for each equipment
        //check if equipments are avialbe and validate Date
    	 String title = "Add Reservation";
    	 if(this.selectedEquipments.size() == 0)
    	     msg = "Please select at least one equipment";

        if(equipmentsAvailable() && validateDate() && withinOpeningHours())
        {

            for(Equipment newEquipment : this.selectedEquipments)
            {
                addEquipmentToReservations(newEquipment);
            }
            msg = "Reservation(s) added successfully";
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml?addedSuccessfully");
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        PrimeFaces.current().dialog().showMessageDynamic(message);
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
					msg = "Equipment is not available";
			        return false;
				}
				if(!(er.getEquipment().isWithinMaxReservationDuration(this.getLendingDate(), this.getReturnDate()))) {
					msg = "Duration too long";
					return false;
				}
			}

		}
		return true;
	}

    /**
     * checks if selected equipments are available and lendingdate und returndate in maxduration
     */
    public boolean equipmentsAvailable()
    {
        //TODO: add check lendingdate and returndate is in maxduration

        if(selectedEquipments == null || lendingDate == null || returnDate == null)
            return false;

        //check if equipments are avialable
        List<Equipment> freeEquipments = (List<Equipment>) equipmentService.getAllFreeEquipments(lendingDate, returnDate);

        for(Equipment equipment : this.selectedEquipments)
        {
            if(!freeEquipments.contains(equipment))
            {
                msg = "Equipment is not available";
                return false;
            }
        }

        return true;
    }
}
