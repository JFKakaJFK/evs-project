package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entity for equipment groups.
 */
@Entity
public class EquipmentGroup implements Persistable<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private User createdBy;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Equipment> equipments;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public boolean isWithinMaxReservationDuration(Date startDate, Date endDate){
        if(equipments == null){
            return true;
        }
        for(Equipment e: equipments){
            if(!e.isWithinMaxReservationDuration(startDate, endDate)){
                return false;
            }
        }
        return true;
    }

    public boolean isAvailable(Date startDate, Date endDate){
        if(equipments == null){
            return true;
        }
        for(Equipment e: equipments){
            if(!e.isAvailable(startDate, endDate)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }
}
