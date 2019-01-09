package at.qe.sepm.skeleton.model;

import java.util.Collection;
import java.util.Date;

public interface Reservation {
    public ReservationType getType();

    Collection<Equipment> getEquipment();

    Date getEndDate();

    Date getStartDate();
}
