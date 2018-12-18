package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Equipment implements Persistable<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String labName;
    @Column(nullable = false)
    private String labLocation;
    @Column(nullable = false)
    private EquipmentState state;
    @Column(nullable = false)
    private Long maxDurationMilliseconds;

    @OneToMany
    private List<EquipmentComment> comments;

    @OneToMany
    private List<EquipmentManual> manuals;

    @OneToMany
    private List<EquipmentReservation> reservations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabLocation() {
        return labLocation;
    }

    public void setLabLocation(String labLocation) {
        this.labLocation = labLocation;
    }

    public EquipmentState getState() {
        return state;
    }

    public void setState(EquipmentState state) {
        this.state = state;
    }

    public Long getMaxDurationMilliseconds() {
        return maxDurationMilliseconds;
    }

    public void setMaxDurationMilliseconds(Long maxDurationMilliseconds) {
        this.maxDurationMilliseconds = maxDurationMilliseconds;
    }

    public List<EquipmentComment> getComments() {
        return comments;
    }

    public void setComments(List<EquipmentComment> comments) {
        this.comments = comments;
    }

    public List<EquipmentManual> getManuals() {
        return manuals;
    }

    public void setManuals(List<EquipmentManual> manuals) {
        this.manuals = manuals;
    }

    public boolean isWithinMaxReservationDuration(Date startDate, Date endDate){
        return (endDate.getTime() - startDate.getTime()) <= maxDurationMilliseconds;
    }

    public boolean isAvailable(Date startDate, Date endDate){
        // TODO
        return false;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Equipment)){
            return false;
        }
        final Equipment other = (Equipment) obj;
        return Objects.equals(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ name =" + name + " ]";
    }

    @Override
    public boolean isNew() {
        // TODO
        return false;
    }
}
