package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Entity for single equipment Reservations
 */
@Entity
public class EquipmentReservation implements Persistable<Integer> {

    private static final long serialVersionUID = 1L;

    @Transient
    private static final int BUFFER_TIME = 2;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Equipment equipment;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(nullable = false)
    private boolean completed = false;

    /**
     * Returns true if the reservation is overdue
     */
    public boolean isOverdue(){
        return equipment.getState() == EquipmentState.OVERDUE && !completed && new Date().getTime() > endDate.getTime();
    }

    /**
     * Returns the endDate if the reservation is not overdue, else the current date plus BUFFER_TIME
     *
     * @return
     */
    public Date getEndDateOverdue(){
        if (isOverdue()){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, BUFFER_TIME);
            return c.getTime();
        } else {
            return endDate;
        }
    }

    /**
     * A reservation is deletable if it hasn't begun yet
     *
     * @return
     */
    public boolean isDeletable(){
        return new Date().before(this.getStartDate()) || completed;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }
}

