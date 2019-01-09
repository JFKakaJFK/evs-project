package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entity for single equipment Reservations
 */
@Entity
public class EquipmentReservation implements Persistable<Integer>, Reservation {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "EQUIPMENT_RESERVATION", joinColumns = {
        @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    },
        inverseJoinColumns = {
            @JoinColumn(name = "equipment_id", referencedColumnName = "id")
        }
    )
    private Set<Equipment> equipment;

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

    public String getEquipmentAsString(){
        return equipment.toString().substring(1, equipment.toString().length() - 1);
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }

    @Override
    public ReservationType getType() {
        return ReservationType.SIMPLE;
    }
}

