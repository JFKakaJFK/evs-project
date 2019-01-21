package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Equipment;
import at.qe.sepm.skeleton.model.EquipmentReservation;
import at.qe.sepm.skeleton.model.Mail;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.EquipmentReservationService;
import at.qe.sepm.skeleton.services.MailService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchedulerBean {
    @Autowired
    private EquipmentReservationService equipmentReservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    //execute all 5mins
    @Scheduled(fixedRate = 50000)
    public void checkReservations()
    {
        List<User> users = new ArrayList<>();
        users.addAll(userService.getAllUsers());

        if(users.size() > 0)
        {
            for(User user : users)
            {
                sendEmailBeforeOverdue(user);
                sendEmailOverdue(user);
            }


        }
    }

    /**
     * Sends Email to given user and all admins, if at least one equipment of the given user is overdue to return
     * @param user
     */
    public void sendEmailOverdue(User user)
    {
        List<EquipmentReservation> reservations = new ArrayList<>();
        reservations.addAll(equipmentReservationService.getAllOverdueReservations(user));

        if(!reservations.isEmpty())
        {
            String userName = user.getFirstName()+" "+user.getLastName();
            StringBuilder emailHeader = new StringBuilder();
            StringBuilder emailHeaderUser = new StringBuilder();
            StringBuilder equipmentsOverdue = new StringBuilder();
            StringBuilder emailFooter = new StringBuilder();

            //header need for both user and admin
            emailHeader.append("<html>");
            emailHeader.append("<body>");

            //header-part just for user
            emailHeaderUser.append("Hallo "+ userName + "!<br><br>");
            emailHeaderUser.append("Folgende Laborgeräte sind überfällig zur Rückgabe:<br>");

            //list of equipments which are overdue (for both)
            equipmentsOverdue.append("<ul>");
            for(EquipmentReservation reservation : reservations)
            {
                equipmentsOverdue.append("<li>" + reservation.getEquipment().getName() + "</li>");
                reservation.setOverdueMailSent(true);
                equipmentReservationService.saveReservation(reservation);
            }
            equipmentsOverdue.append("</ul>");

            //email Footer for both
            emailFooter.append("<p>Dies ist eine automatisch generierte Email, bitte nicht antworten!</p>");
            emailFooter.append("</body>");
            emailFooter.append("</html>");

            //send Email to User:
            StringBuilder emailContentUser = new StringBuilder();
            emailContentUser.append(emailHeader);
            emailContentUser.append(emailHeaderUser);
            emailContentUser.append(equipmentsOverdue);
            emailContentUser.append(emailFooter);

            Mail mail = new Mail(user.getEmail(), "Überfällige Buchungen", emailContentUser.toString());
            mailService.sendMail(mail); //send mail

            //Send Email to Admins
            List<User> admins = (List<User>) userService.getAllAdminUsers();
            for(User admin : admins)
            {
                StringBuilder emailContentAdmin = new StringBuilder();
                emailContentAdmin.append(emailHeader);
                emailContentAdmin.append("Hallo " + admin.getFirstName() + " " + admin.getLastName() + "!<br><br>");
                emailContentAdmin.append("Folgende Laborgeräte von Benutzer " + userName + "(" + user.getcNumber() + ") sind überfällig zur Rückgabe:<br>");
                emailContentAdmin.append(equipmentsOverdue);
                emailContentAdmin.append(emailFooter);

                Mail mailAdmin = new Mail(admin.getEmail(), "Überfällige Buchung (" + user.getcNumber() + ")", emailContentAdmin.toString());
                mailService.sendMail(mailAdmin);
            }
        }
    }

    /**
     * Sends an reminder-Email to the given user, if reservations / booking duration is >= 3 days
     * @param user
     */
    public void sendEmailBeforeOverdue(User user)
    {
        List<EquipmentReservation> reservations = new ArrayList<>();
        reservations.addAll(equipmentReservationService.getAllLongReservationsEndingSoonByUser(user));

        if(!reservations.isEmpty())
        {
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html>");
            emailContent.append("<body>");

            emailContent.append("Hallo "+user.getFirstName()+" "+user.getLastName()+"!<br><br>");
            emailContent.append("Folgende Laborgeräte sind innerhalb von 24h zurückzugeben:<br>");

            emailContent.append("<ul>");
            for(EquipmentReservation reservation : reservations)
            {
                emailContent.append("<li>" + reservation.getEquipment().getName() + "</li>");
                reservation.setReminderMailSent(true);
                equipmentReservationService.saveReservation(reservation);
            }
            emailContent.append("</ul>");

            emailContent.append("<p>Dies ist eine automatisch generierte Email, bitte nicht antworten!</p>");

            emailContent.append("</body>");
            emailContent.append("</html>");

            //Create Mail
            Mail mail = new Mail(user.getEmail(), "Eerinnerung Buchungen", emailContent.toString());
            mailService.sendMail(mail); //send mail
        }
    }
}
