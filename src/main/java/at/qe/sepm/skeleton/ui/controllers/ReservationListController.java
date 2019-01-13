package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private EquipmentService equipmentService;

	@Autowired
    private UserService userService;

    private List<EquipmentReservation> defaultReservationsReturn;
    private List<EquipmentReservation> selectedReservationsReturn;
    private List<EquipmentReservation> filteredReservationsReturn;

    @PostConstruct
    public void Init()
    {
        this.defaultReservationsReturn = new ArrayList<>();
        this.defaultReservationsReturn.addAll(equipmentReservationService.getAllBorrowedEquipments());
    }

    public List<EquipmentReservation> getDefaultReservationsReturn() {
        return defaultReservationsReturn;
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

    public void doEquipmentReturn()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Equipment returnation", selectedReservationsReturn.size()+" has been returnd"));

        if(selectedReservationsReturn.size() > 0)
        {
            for(EquipmentReservation equipmentReservation : selectedReservationsReturn)
            {
                EquipmentReservation res = equipmentReservationService.loadRerservation(equipmentReservation.getId());
                res.setCompleted(true);
                equipmentReservationService.saveReservation(res);
            }


        }
    }
}
