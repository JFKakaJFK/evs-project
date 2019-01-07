package at.qe.sepm.skeleton.model;

import org.springframework.data.annotation.Persistent;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity representing Holidays
 */
@Entity
public class Holidays implements Persistable<Long> {

    @Id
    @GeneratedValue
    private long id;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
