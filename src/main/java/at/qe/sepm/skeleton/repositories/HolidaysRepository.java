package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Holidays;
import at.qe.sepm.skeleton.model.OpeningHours;

public interface HolidaysRepository extends AbstractRepository<Holidays, Long>  {
    //OpeningHours findFirstByDay(String day);
}
