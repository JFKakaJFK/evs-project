package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.OpeningHoursRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Service for accessing and manipulating openinghours related data.
 */
@Component
@Scope("application")
public class OpeningHoursService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpeningHoursRepository openingHoursRepository;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OpeningHoursService.class);

    /**
     * Returns a collection of all days of openinghours.
     *
     * @return
     */
    public Collection<OpeningHours> getAllOpeningHours()
    {
        return openingHoursRepository.findAll();
    }

    /**
     * Loads a single day of openinghours identified by its day.
     *
     * @param day to search for
     * @return the day of openinghour with the given day
     */
    public OpeningHours loadOpeningHour(String day)
    {
        return openingHoursRepository.findFirstByDay(day);
    }

    /**
     * Saves a new day for openngHours. This method will also set {@link OpeningHours#createDate} for new
     * entities or {@link OpeningHours#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link OpeningHours#createDate}
     * or {@link OpeningHours#updateUser} respectively.
     *
     * @param openingHour the new day of openingHours to save
     * @return the updated openingHour
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public OpeningHours saveOpeningHour(OpeningHours openingHour)
    {
        if(openingHour.isNew())
        {
            openingHour.setCreateDate(new Date());
            openingHour.setCreateUser(getAuthenticatedUser());
        }

        else
        {
            openingHour.setUpdateDate(new Date());
            openingHour.setUpdateUser(getAuthenticatedUser());
        }

        return openingHoursRepository.save(openingHour);
    }

    public boolean isWithinOpeningHours(Date date) {
        OpeningHours openingHours = this.loadOpeningHour(getWeekDay(date));
        if(openingHours == null)
            return false;

        try
        {
            DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = simpleDateFormat.format(date);
            date = simpleDateFormat.parse(time);

            openingHours = this.loadOpeningHour(getWeekDay(date));
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }




        if(openingHours != null)
        {
            if(openingHours.getStartPause() == null || openingHours.getEndPause() == null)
            {
                if((date.after(openingHours.getStartTime()) || date.equals(openingHours.getStartTime()))
                    &&
                    (date.before(openingHours.getEndTime()) || date.equals(openingHours.getEndTime())))
                {
                    return true;
                }
                //check just startTime and endTime
            }

            else
            {
                //check sartTime, endTime + startPause, endPause
                if(
                    (date.after(openingHours.getStartTime()) || date.equals(openingHours.getStartTime())) && (date.before(openingHours.getStartPause()) || date.equals(openingHours.getStartPause()))
                        ||
                    (
                        (date.after(openingHours.getEndPause()) || date.equals(openingHours.getEndPause())) && (date.before(openingHours.getEndTime()) || date.equals(openingHours.getEndTime())))
                )

                {
                    return true;
                }

            }
        }

        return false;
    }

    public String getWeekDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek)
        {
            case Calendar.MONDAY:
                return "Montag";
            case Calendar.TUESDAY:
                return "Dienstag";
            case Calendar.WEDNESDAY:
                return "Mittwoch";
            case Calendar.THURSDAY:
                return "Donnerstag";
            case Calendar.FRIDAY:
                return "Freitag";
            case Calendar.SATURDAY:
                return "Samstag";
            case Calendar.SUNDAY:
                return "Sonntag";
        }
        return null;
    }

    /**
     * Deletes the day of openinghour
     *
     * @param openingHour day of openingHour to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteOpeningHour(OpeningHours openingHour) {
        openingHoursRepository.delete(openingHour);
        logger.warn("DELETED OpeningHour: " + openingHour + " (by " + getAuthenticatedUser() + ")");
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
