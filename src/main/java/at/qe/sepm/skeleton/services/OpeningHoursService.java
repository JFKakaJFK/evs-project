package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.OpeningHours;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.OpeningHoursRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Date;

public class OpeningHoursService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpeningHoursRepository openingHoursRepository;

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

    /**
     * Deletes the day of openinghour
     *
     * @param openingHour day of openingHour to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteOpeningHour(OpeningHours openingHour) {
        openingHoursRepository.delete(openingHour);
        // :TODO: write some audit log stating who and when this day was permanently deleted.
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }
}
