package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Component
@Scope("view")
public class ReservationListController implements Serializable {
	@Autowired
	private EquipmentReservationService equipmentReservationService;

	@Autowired
    private UserService userService;

	@Autowired
    private SessionInfoBean sessionInfoBean;

    private List<EquipmentReservation> defaultReservationsReturn;
    private List<EquipmentReservation> selectedReservationsReturn;
    private List<EquipmentReservation> filteredReservationsReturn;
    private List<EquipmentReservation> allReservationsReturn;

    private Equipment equipmentManual;

    @Deprecated
    private boolean returnedSuccessfully = false;

    @PostConstruct
    public void Init()
    {
        if(sessionInfoBean.hasRole("ADMIN"))
        {
            this.defaultReservationsReturn = new ArrayList<>();
            this.defaultReservationsReturn.addAll(equipmentReservationService.getAllBorrowedEquipments());
            this.allReservationsReturn= new ArrayList<>();
            this.allReservationsReturn.addAll(equipmentReservationService.getAllEquipmentReservations());
        }
    }

    public boolean isReturnedSuccessfully() {
        return returnedSuccessfully;
    }

    public void setReturnedSuccessfully(boolean returnedSuccessfully) {
        this.returnedSuccessfully = returnedSuccessfully;
    }

    public List<EquipmentReservation> getDefaultReservationsReturn() {
        return defaultReservationsReturn;
    }

    public List<EquipmentReservation> getAllReservationsReturn() {
        return allReservationsReturn;
    }

    public void setAllReservationsReturn(List<EquipmentReservation> allReservationsReturn) {
        this.allReservationsReturn = allReservationsReturn;
    }

    public List<EquipmentReservation> getFilteredReservationsReturn() {
        return filteredReservationsReturn;
    }

    public void setFilteredReservationsReturn(List<EquipmentReservation> filteredReservationsReturn) {
        this.filteredReservationsReturn = filteredReservationsReturn;
    }

    public List<EquipmentReservation> getSelectedReservationsReturn() {
        return selectedReservationsReturn;
    }

    public void setSelectedReservationsReturn(List<EquipmentReservation> selectedReservationsReturn) {
        this.selectedReservationsReturn = selectedReservationsReturn;
    }

    public Collection<EquipmentReservation> getReservations() {
		return equipmentReservationService.getAllEquipmentReservations();
	}

	public Collection<EquipmentReservation> getAllUserReservations()
    {
        User authUser = userService.getAuthenticatedUser();

        return equipmentReservationService.getAllByUser(authUser);
    }

    public Equipment getEquipmentManual() {
        return equipmentManual;
    }

    public void setEquipmentManual(Equipment equipmentManual) {
        this.equipmentManual = equipmentManual;
    }

    /**
     * Set for each reservation in ReservationListController#selectedReservationsReturn the attribute completed 'true'
     * removes all returned reservations in ReservationListController#defaultReservationsReturn and
     * ReservationListController#filteredReservationsReturn
     */
    public void doEquipmentReturn()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if(selectedReservationsReturn.size() > 0)
        {
            for(EquipmentReservation equipmentReservation : selectedReservationsReturn)
            {
                EquipmentReservation res = equipmentReservationService.loadReservation(equipmentReservation.getId());
                res.setCompleted(true);
                equipmentReservationService.saveReservation(res);
            }

            this.defaultReservationsReturn.clear();
            this.defaultReservationsReturn.addAll(this.equipmentReservationService.getAllBorrowedEquipments());

            if(this.filteredReservationsReturn != null)
            {
                this.filteredReservationsReturn.clear();
                this.filteredReservationsReturn.addAll(this.equipmentReservationService.getAllBorrowedEquipments());
            }

            //Equipments successfully returned
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Rückgabe",
                selectedReservationsReturn.size()+" Geräte erfolgreich zurückgegeben."
            ));
        }

        else
        {
            //no equipment selected
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rückgabe", "Bitte min. 1 Gerät auswählen"));
        }
    }

    @Deprecated
    public void checkURL() {
        Iterator<String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterNames();
        if(params.hasNext()) {
            String parameter = params.next();
            if(parameter.equals("returnedSuccessfully")) {
                this.returnedSuccessfully = true;
            }
        }
    }
}
