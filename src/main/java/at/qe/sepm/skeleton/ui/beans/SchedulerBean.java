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

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.Y");


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
                sendEmailsBeforeOverdue(user);
                sendEmailsOverdue(user);
            }


        }
    }


    public void sendEmailsOverdue(User user)
    {
        List<EquipmentReservation> reservations = new ArrayList<>();
        reservations.addAll(equipmentReservationService.getAllOverdueReservations(user));

        if(!reservations.isEmpty())
        {
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html>");
            emailContent.append("<body>");

            emailContent.append("Hallo "+user.getFirstName()+" "+user.getLastName()+"!<br><br>");
            emailContent.append("Folgende Laborgeräte sind überfällig zur Rückgabe:<br>");

            emailContent.append(
                "<style>" +
                    "table { " +
                    "  text-align: left;" +
                    "  border-collapse: collapse; " +
                    "  width: 100%; " +
                    "}" +
                    "" +
                    "td, th { " +
                    "  border: 1px solid; " +
                    "  padding: .5em; " +
                    "}" +
                    "</style>"
            );
            emailContent.append("<table>");
            emailContent.append("<thead>");
            emailContent.append("<tr>");
            emailContent.append("<td>Laborgerät</td>");
            emailContent.append("<td>Rückgabedatum</td>");
            emailContent.append("</tr>");
            emailContent.append("</thead>");

            emailContent.append("<tbody>");
            for(EquipmentReservation reservation : reservations)
            {
                emailContent.append("<tr>");
                emailContent.append("<td>" + reservation.getEquipment().getName() + "</td>");
                emailContent.append("<td>" + simpleDateFormat.format(reservation.getEndDate()) + "</td>");
                emailContent.append("</tr>");

                reservation.setOverdueMailSent(true);
                equipmentReservationService.saveReservation(reservation);
            }
            emailContent.append("</tbody>");
            emailContent.append("</table>");

            emailContent.append("<p>Dies ist eine automatisch generierte Email, bitte nicht antworten!</p>");

            emailContent.append("</body>");
            emailContent.append("</html>");

            //Create Mail
            Mail mail = new Mail(user.getEmail(), "Überfällige Buchungen", emailContent.toString());
            mailService.sendMail(mail); //send mail
        }
    }

    public void sendEmailsBeforeOverdue(User user)
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
