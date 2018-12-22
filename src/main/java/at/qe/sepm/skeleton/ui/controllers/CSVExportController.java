package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Collection;

/**
 * Controller to export users to a CSV file.
 */

@Component
@Scope("request")
public class CSVExportController {

    @Autowired
    private UserService userService;

    public void exportCSV() {

        final String commaDelimiter = ",";
        final String lineSeparator = "\n";
        final String header = "Username,Password,FirstName,LastName,CreateDate,Enabled";

        Collection<User> usersToExport = userService.getAllUsers();
        FileWriter writer = null;
        try{
            writer = new FileWriter("src/main/webapp/downloadCSV/Users.csv");
            writer.append(header);
            writer.append(lineSeparator);

            for(User u : usersToExport) {
                writer.append(u.getUsername());
                writer.append(commaDelimiter);
                writer.append(u.getPassword());
                writer.append(commaDelimiter);
                writer.append(u.getFirstName());
                writer.append(commaDelimiter);
                writer.append(u.getLastName());
                writer.append(commaDelimiter);
                writer.append(String.valueOf(u.getCreateDate()));
                writer.append(commaDelimiter);
                writer.append(String.valueOf(u.isEnabled()));
                writer.append(commaDelimiter);
                writer.append(lineSeparator);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                FacesContext.getCurrentInstance().getExternalContext().redirect("../downloadCSV/Users.csv");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
