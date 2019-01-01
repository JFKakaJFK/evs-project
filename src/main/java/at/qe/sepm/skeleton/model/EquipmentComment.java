package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity for equipment comments
 */
@Entity
public class EquipmentComment implements Persistable<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }
}
