package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentGroup;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.services.EquipmentGroupService;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.UserService;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Scope("view")
public class newGroupReservationController extends ReservationController implements Serializable {
    private EquipmentGroup detailGroup;

    private List<EquipmentGroup> defaultGroups;
    private List<EquipmentGroup> selectedGroups;
    private List<EquipmentGroup> filteredGroups;

    @Autowired
    private EquipmentGroupService equipmentGroupService;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private UserService userService;

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
     * @param event
     */
    public void dateChanged(SelectEvent event)
    {
        if(this.lendingDate != null && this.returnDate != null)
        {
            //ToDo: check max duration

            //check landing- and returndate
            if(validateDate())
            {
                List<EquipmentGroup> freeGroups = new ArrayList<>();
                if(withinOpeningHours())
                {
                    freeGroups.addAll(equipmentGroupService.getOwnGroupsFree(this.lendingDate, this.returnDate));
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
     * add selected groups in selectedGroups to database
     * */
    public void addGroupReservation() throws IOException
    {
        //TODO: error and success information

        String title = "Reservierung hinzufügen";
        if(this.selectedGroups.size() == 0)
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
            msg = "Reservierung wurde erfolgreich hinzugefügt";
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml?addedSuccessfully");
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public boolean groupsAvailable()
    {
        if(selectedGroups == null)
        {
            return false;
        }

        return true;
    }
}
