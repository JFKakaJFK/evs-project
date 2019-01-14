package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class newGroupReservationController extends ReservationController implements Serializable {
    private EquipmentGroup detailGroup;

    private List<EquipmentGroup> defaultGroups;
    private List<EquipmentGroup> selectedGroups;
    private List<EquipmentGroup> filteredGroups;

    @Autowired
    private EquipmentGroupService equipmentGroupService;

   @PostConstruct
    public void Init()
   {
       scheduleModel = new DefaultScheduleModel();

       defaultGroups = new ArrayList<>();
       defaultGroups.addAll(this.equipmentGroupService.getOwnGroups());
       addedSuccessfully = false;
   }


    public List<EquipmentGroup> getDefaultGroups() {
        return defaultGroups;
    }

    public EquipmentGroup getDetailGroup() {
        return detailGroup;
    }

    public void setDetailGroup(EquipmentGroup detailGroup) {
        this.detailGroup = detailGroup;
    }

    public List<EquipmentGroup> getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(List<EquipmentGroup> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public List<EquipmentGroup> getFilteredGroups() {
        return filteredGroups;
    }

    public void setFilteredGroups(List<EquipmentGroup> filteredGroups) {
        this.filteredGroups = filteredGroups;
    }

    /**
     * Eventlistener which filters the equipmentgroups by landingdate and returningdate
     */
    public void dateChanged()
    {
        if(this.lendingDate != null && this.returnDate != null)
        {
            //check landing- and returndate
            if(validateDate())
            {
                List<EquipmentGroup> freeGroups = new ArrayList<>();
                if(withinOpeningHours())
                {
                    freeGroups.addAll(equipmentGroupService.getOwnGroupsFree(this.lendingDate, this.returnDate));

                    //removes groups which are not within max reservation duration
                    freeGroups = freeGroups.stream()
                        .filter(p -> p.isWithinMaxReservationDuration(this.lendingDate, this.returnDate))
                        .collect(Collectors.toList());

                    if(freeGroups.size() == 0)
                    {
                        //no freeGroups within max reservation duration
                        showErrorMessage("Keine Gerätegruppe ist in diesem Ausleihzeitraum verfübar");
                    }
                }

                else
                {
                    //selected dates are not in openinghours
                    showErrorMessage("Ausleih und/oder Rückgabedatum sind nicht innerhalb der Öffnungszeiten");
                }

                if(filteredGroups == null)
                {
                    this.defaultGroups.clear();
                    this.defaultGroups.addAll(freeGroups);
                }

                else
                {
                    List<EquipmentGroup> toRemove = new ArrayList<>();
                    toRemove.addAll(filteredGroups);

                    toRemove.removeAll(freeGroups);
                    filteredGroups.removeAll(toRemove);
                    defaultGroups.clear();
                    defaultGroups.addAll(filteredGroups);
                }
            }
        }
    }

    /**
     * Eventlistener which add the reservations of a specific group to the calender
     * @return null
     */
    public String calendarAction()
    {
        scheduleModel.clear();

        int equipmentGroupID = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("detailGroup"));
        detailGroup = this.equipmentGroupService.loadEquipmentGroup(equipmentGroupID);

        //update calender infos
        for(Equipment equipment : detailGroup.getEquipments())
        {
            addReservationsOfEquipmentToSchedule(equipment);
        }

        return null;
    }

    /**
     * Resets all Filter Inputs (Search, Landing- and Return date)
     */
    public void resetInputs()
    {
        this.defaultGroups.clear();
        this.defaultGroups.addAll(this.equipmentGroupService.getOwnGroups());

        this.lendingDate = null;
        this.returnDate = null;

        PrimeFaces.current().executeScript("PF('groupSelect').clearFilters()");
        PrimeFaces.current().resetInputs("newReservation:newReservationPanel");
    }

    /**
     * add equipments of selectedgroups to equipmentsreservations
     * */
    public void addGroupReservation()
    {
        if(this.selectedGroups.size() == 0)
        {
            showErrorMessage("Min. eine Gruppe muss ausgewählt werden.");
        }
            msg = "Bitte wählen Sie mindestens eine Laborgruppe aus";

        if(groupsAvailable() && validateDate() && withinOpeningHours())
        {

            for(EquipmentGroup group : this.selectedGroups)
            {
                for(Equipment newEquipment : group.getEquipments())
                {
                    addEquipmentToReservations(newEquipment);
                }
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
     * Checks if selected groups are available and lendingdate und returndate in maxduration time
     * @return boolean
     */
    private boolean groupsAvailable()
    {
        if(selectedGroups == null)
        {
            return false;
        }

        //check if equipments are avialable
        List<EquipmentGroup> freeGroups = (List<EquipmentGroup>) equipmentGroupService.getOwnGroupsFree(lendingDate, returnDate);

        for(EquipmentGroup equipmentGroup : selectedGroups)
        {
            if(!freeGroups.contains(equipmentGroup))
            {
                showErrorMessage("Min. eine Gruppe ist nicht verfügbar.");
                return false;
            }

            if(!equipmentGroup.isWithinMaxReservationDuration(this.lendingDate, this.returnDate))
            {
                showErrorMessage("Die Maximaleausleihdauer wurde überschritten.");
                return false;
            }
        }

        return true;
    }
}
