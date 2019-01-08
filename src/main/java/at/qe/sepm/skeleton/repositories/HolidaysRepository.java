package at.qe.sepm.skeleton.repositories;


import at.qe.sepm.skeleton.model.Holidays;

public interface HolidaysRepository extends AbstractRepository<Holidays, Long> {
    Holidays findFirstByName(String Name);
    Holidays findFirstById(Long id);
}
