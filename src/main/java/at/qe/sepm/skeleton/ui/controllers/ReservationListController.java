package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.EquipmentService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@Component
@Scope("view")
public class ReservationListController implements Serializable {
	@Autowired
	private EquipmentReservationService equipmentReservationService;

	@Autowired
    private EquipmentService equipmentService;

	@Autowired
    private UserService userService;

	@Autowired
    private SessionInfoBean sessionInfoBean;

    private List<EquipmentReservation> defaultReservationsReturn;
    private List<EquipmentReservation> selectedReservationsReturn;
    private List<EquipmentReservation> filteredReservationsReturn;

    private boolean returnedSuccessfully = false;

    @PostConstruct
    public void Init()
    {
        if(sessionInfoBean.hasRole("ADMIN"))
        {
            this.defaultReservationsReturn = new ArrayList<>();
            this.defaultReservationsReturn.addAll(equipmentReservationService.getAllBorrowedEquipments());
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
        if(selectedReservationsReturn.size() > 0)
        {
            for(EquipmentReservation equipmentReservation : selectedReservationsReturn)
            {
                EquipmentReservation res = equipmentReservationService.loadRerservation(equipmentReservation.getId());
                res.setCompleted(true);
                equipmentReservationService.saveReservation(res);
            }

            this.defaultReservationsReturn.clear();
            this.defaultReservationsReturn.addAll(this.equipmentReservationService.getAllBorrowedEquipments());

            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Equipment returnation",
                selectedReservationsReturn.size()+" equipments returned successfully"
            ));
        }

        else
        {
            //no equipment selected
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Equipment returnation", "Please select at least one equipment"));
        }
    }

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
