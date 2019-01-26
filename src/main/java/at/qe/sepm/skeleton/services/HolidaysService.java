package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.HolidaysRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for accessing and manipulating holiday data.
 */
@Component
@Scope("application")
public class HolidaysService {
    @Autowired
    private HolidaysRepository holidaysRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns a collection of all holidays.
     *
     * @return
     */
    public Collection<Holidays> getAllHolidays() {
        return holidaysRepository.findAll();
    }

    /**
     * Loads a single holiday identified by its name.
     *
     * @param name the name to search for
     * @return the holiday with the given name
     */

    public Holidays loadHoliday(String name) {
        return holidaysRepository.findFirstByName(name);
    }

    /**
     * Saves a holiday. This method will also set {@link Holidays#createDate} for new
     * entities or {@link Holidays#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link Holidays#createDate}
     * or {@link Holidays#updateUser} respectively.
     *
     * @param day the holiday to save
     * @return the updated day
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Holidays saveHoliday(Holidays day) {
        if (day.isNew()) {
            day.setCreateDate(new Date());
            day.setCreateUser(getAuthenticatedUser());
        } else {
            day.setUpdateDate(new Date());
            day.setUpdateUser(getAuthenticatedUser());
        }
        return holidaysRepository.save(day);
    }

    /**
     * Return true if checkDate is within a Holiday
     * @param checkDate
     * @return
     */
    public boolean isWithinHolidays(Date checkDate)
    {

        List<Holidays> holidays = new ArrayList<>(getAllHolidays());

        for(Holidays holiday : holidays)
        {
            //checkDate is within Holiday
            if(isWithinDateRange(checkDate, holiday.getStartDate(), holiday.getEndDate()))
                return true;
        }

        return false;
    }

    /**
     * Returns true if checkDate is within the range between startDate and endDate
     * @param checkDate
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isWithinDateRange(Date checkDate, Date startDate, Date endDate)
    {
        if(checkDate.equals(startDate) || checkDate.equals(endDate))
            return true;

        return checkDate.after(startDate) && checkDate.before(endDate);
    }

    /**
     * Deletes the holiday.
     *
     * @param day the holiday to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteHoliday(Holidays day) {
        holidaysRepository.delete(day);
        // :TODO: write some audit log stating who and when this user was permanently deleted.
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
