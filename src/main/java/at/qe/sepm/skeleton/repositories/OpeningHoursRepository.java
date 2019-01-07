package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.OpeningHours;

public interface OpeningHoursRepository extends AbstractRepository<OpeningHours, Long> {
    OpeningHours findFirstByDay(String day);
    OpeningHours findFirstById(Long id);
}
