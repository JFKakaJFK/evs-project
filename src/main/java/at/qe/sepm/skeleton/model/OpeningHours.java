package at.qe.sepm.skeleton.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

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
    private Time startTime;
    @Temporal(TemporalType.TIME)
    private String endTime;
    @Temporal(TemporalType.TIME)
    private String startPause;
    @Temporal(TemporalType.TIME)
    private String endPause;

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


    public void setId(long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartPause() {
        return startPause;
    }

    public void setStartPause(String startPause) {
        this.startPause = startPause;
    }

    public String getEndPause() {
        return endPause;
    }

    public void setEndPause(String endPause) {
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
