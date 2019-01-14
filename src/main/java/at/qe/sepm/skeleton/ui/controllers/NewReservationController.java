package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.services.EquipmentService;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class NewReservationController extends ReservationController implements Serializable {
    private List<Equipment> selectedEquipments;
    private List<Equipment> filteredEquipments;
    private List<Equipment> defaultEquipments;

    private Equipment detailEquipment;

    @Autowired
    private EquipmentService equipmentService;

    @PostConstruct
    public void Init()
    {
        scheduleModel = new DefaultScheduleModel();
        defaultEquipments = new ArrayList<>();
        defaultEquipments.addAll(equipmentService.getAllEquipments());
        addedSuccessfully = false;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //              GETTER AND SETTER
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<Equipment> getDefaultEquipments() {
        return defaultEquipments;
    }

    public void setDefaultEquipments(List<Equipment> defaultEquipments) {
        this.defaultEquipments = defaultEquipments;
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //              SPECIFIC METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Eventlistener which add the reservations of a specific equipment to the calender
     * @return null
     */
    public String calendarAction()
    {
        scheduleModel.clear();

        int equipmentID = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("detailEquipment"));
        detailEquipment = equipmentService.loadEquipment(equipmentID);

        //update calender infos
        addReservationsOfEquipmentToSchedule(detailEquipment);

        return null;
    }

    /**
     * Eventlistener which filters the equipments by landingdate and returningdate
     */
    public void dateChanged()
    {
        if(this.lendingDate != null && this.returnDate != null)
        {
            if(validateDate())
            {
                Collection<Equipment> freeEquipments = null;
                if(withinOpeningHours())
                {
                    freeEquipments = equipmentService.getAllFreeEquipments(lendingDate, returnDate);

                    //removes equipments which are not within max reservation duration
                    freeEquipments = freeEquipments.stream()
                        .filter(p -> p.isWithinMaxReservationDuration(lendingDate, returnDate)).collect(Collectors.toList());

                    if(freeEquipments.size() == 0)
                    {
                        //no freeEquipments within max reservation duration
                        showErrorMessage("Kein Gerät ist in diesem Ausleihzeitraum verfübar");
                    }
                }

                else
                {
                    //selected dates are not in openinghours
                    showErrorMessage("Ausleih und/oder Rückgabedatum sind nicht innerhalb der Öffnungszeiten");
                }

                //replace
                if(filteredEquipments == null)
                {
                    this.defaultEquipments.clear();
                    if(freeEquipments != null)
                    {
                        this.defaultEquipments.addAll(freeEquipments);
                    }
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
        this.defaultEquipments.clear();
        this.defaultEquipments.addAll(equipmentService.getAllEquipments());
        this.lendingDate = null;
        this.returnDate = null;

        PrimeFaces.current().executeScript("PF('equipmentSelect').clearFilters()");
        PrimeFaces.current().resetInputs("newReservation:newReservationPanel");
    }


    /**
     * add selected equipments from NewReservationController#selectedEquipments to EquipmentReservation
     */
    public void addEquipmentReservation() {
         //check if at least one equipment is selected
    	 if(this.selectedEquipments.size() == 0)
         {
             showErrorMessage("Min. eine Gerät muss ausgewählt werden.");
         }

        if(selectedEquipmentsAvailable() && validateDate() && withinOpeningHours())
        {

            for(Equipment newEquipment : this.selectedEquipments)
            {
                addEquipmentToReservations(newEquipment);
            }

            //redirect to welcome page
            try
            {
                FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml?addedSuccessfully");
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if selected equipments are available and lendingdate und returndate in maxduration time
     * @return true if equipments are available
     */
    private boolean selectedEquipmentsAvailable()
    {
        if(selectedEquipments == null || lendingDate == null || returnDate == null)
        {
            return false;
        }

        //check if equipments are avialable
        List<Equipment> freeEquipments = (List<Equipment>) equipmentService.getAllFreeEquipments(lendingDate, returnDate);

        for(Equipment equipment : this.selectedEquipments)
        {
            if(!freeEquipments.contains(equipment))
            {
                showErrorMessage("Min. ein Gerät ist nicht verfügbar.");
                return false;
            }

            if(!equipment.isWithinMaxReservationDuration(this.lendingDate, this.returnDate))
            {
                showErrorMessage("Die Maximaleausleihdauer wurde überschritten.");
                return false;
            }
        }

        return true;
    }
}
