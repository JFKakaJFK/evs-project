package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "EQUIPMENT_EQUIPMENTGROUP", joinColumns = {
        @JoinColumn(name = "equipment_group_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "equipment_id", referencedColumnName = "id")
        }
    )
    private Set<Equipment> equipments = new HashSet<>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getEquipmentAsString(){
        return equipments.toString().substring(1, equipments.toString().length() - 1);
    }


    @Override
    public boolean isNew() {
        return (null == user);
    }
}
