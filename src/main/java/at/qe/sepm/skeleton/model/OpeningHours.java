package at.qe.sepm.skeleton.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.data.domain.Persistable;

/**
 * Entity representing Opening Hours
 */
@Entity
public class OpeningHours implements Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String day;

    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Temporal(TemporalType.TIME)
    private Date endPause;
    @Temporal(TemporalType.TIME)
    private Date startPause;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @ManyToOne(optional = true)
    private User updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(length = 100)

    // TODO maybe add verification in setters?  (e.g. the pause needs to be within the opneing hours and so on..)

    public void setId(long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartPause() {
        return startPause;
    }

    public void setStartPause(Date startPause) {
        this.startPause = startPause;
    }

    public Date getEndPause() {
        return endPause;
    }

    public void setEndPause(Date endPause) {
        this.endPause = endPause;
    }



    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return (null == this.createDate);
    }
}
