package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReservationController {
    @Autowired
    protected UserService userService;

     @Autowired
     private MailService mailService;

     @Autowired
     private HolidaysService holidaysService;

    protected boolean addedSuccessfully;
    protected Date lendingDate;
    protected Date returnDate;
    protected ScheduleModel scheduleModel;

    protected String msg;

    @Autowired
    private OpeningHoursService openingHoursService;

    @Autowired
    private EquipmentReservationService equipmentReservationService;

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
        return false;
    }

    /**
     * check if enddate is after startdate and after current date
     * @return true if dates are valid
     */
    public boolean validateDate() {
        if(lendingDate == null || returnDate == null)
        {
            showErrorMessage("Bitte Ausleih- und/oder Rückgabedatum auswählen");
            return false;
        }

        Date today = new Date();
        today.getTime();
        if((today.after(this.returnDate) && today.after(this.lendingDate)) || (returnDate.before(lendingDate))) {
            showErrorMessage("Ungültiges Ausleih und/oder Rückgabedatum");
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

    public void showErrorMessage(String message)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neue Reservierung",  message) );
    }

    public void sendEmail(List<Equipment> equipments)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.Y HH:mm");
        User user = userService.getAuthenticatedUser();

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html>");
        emailContent.append("<body>");

        emailContent.append("Hallo "+user.getFirstName()+" "+user.getLastName()+"!<br><br>");
        emailContent.append("Ihre gewünschten Laborgeräte wurden reserviert<br>");
        emailContent.append("Zeitraum: " + dateFormat.format(lendingDate) + " bis " + dateFormat.format(returnDate)+"<br><br>");

        emailContent.append("Laborgeräte:<br>");
        emailContent.append("<ul>");
        for(Equipment equipment : equipments)
        {
            emailContent.append("<li>" + equipment.getName() + "</li>");

        }
        emailContent.append("</ul>");

        emailContent.append("<p>Dies ist eine automatisch generierte Email, bitte nicht antworten!</p>");

        emailContent.append("</body>");
        emailContent.append("</html>");

        //Create Mail
        Mail mail = new Mail(user.getEmail(), "Neue Reservierung", emailContent.toString());
        mailService.sendMail(mail); //send mail
    }

    /**
     * Adds all holidays to calendar
     */
    public void addHolidaysToSchedular()
    {
        for(Holidays holidays : holidaysService.getAllHolidays())
        {
            scheduleModel.addEvent(new DefaultScheduleEvent(holidays.getName(), holidays.getStartDate(), holidays.getEndDate()));
        }
    }
}
