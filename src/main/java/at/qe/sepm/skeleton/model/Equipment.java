package at.qe.sepm.skeleton.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Entity representing lab equipments.
 */
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

    private boolean locked;

    public boolean isReturned() {
        return returned = true;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public List<EquipmentGroup> getEquipmentGroups() {
        return equipmentGroups;
    }

    public void setEquipmentGroups(List<EquipmentGroup> equipmentGroups) {
        this.equipmentGroups = equipmentGroups;
    }

    private boolean returned = true;

    @Transient
    private EquipmentState state;

    @Column(nullable = false)
    private Long maxDurationMilliseconds;

    //TODO may need to fetch comments/manuals manually
    //@OneToMany(cascade = CascadeType.REMOVE)//, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EquipmentComment> comments;

    //@OneToMany(cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EquipmentManual> manuals = new ArrayList<>();

    //@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    //private List<EquipmentReservation> reservations;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "equipments", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EquipmentGroup> equipmentGroups;


    // TODO Cascading on delete
    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "reservations", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
        reservation.getEquipment().add(this);
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
        reservation.getEquipment().remove(this);
    }

    public void addEquipmentGroup(EquipmentGroup equipmentGroup){
        equipmentGroups.add(equipmentGroup);
        equipmentGroup.getEquipments().add(this);
    }

    public void removeEquipmentGroup(EquipmentGroup equipmentGroup){
        equipmentGroups.remove(equipmentGroup);
        equipmentGroup.getEquipments().remove(this);
    }

    public void remove(){
        for (EquipmentGroup e: equipmentGroups) {
            removeEquipmentGroup(e);
        }
        for (Reservation r: reservations){
            removeReservation(r);
        }
    }

    public EquipmentState getState(Date start, Date end){
        if(locked){
            return EquipmentState.LOCKED;
        } else if(isOverdue()) {
            // TODO if not available
            return EquipmentState.OVERDUE;
        } else if(isAvailable(start, end)) {
            return EquipmentState.AVAILABLE;
        } else {
            return EquipmentState.BOOKED;
        }
    }

    public String getMaxDurationFormatted(){
        return String.format("%dDays, %dHours, %dMinutes",
            TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds),
            TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds)),
            TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds))
        );
    }

    public String getMaxDuration(){
        return String.format("%2d:2%d:%2d",
            TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds),
            TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds)),
            TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds))
        );
    }

    public void setMaxDuration(String duration){
        Scanner sc = new Scanner(duration).useDelimiter(":");
        long millis = 0;
        if(sc.hasNext()){
            millis += TimeUnit.DAYS.toMillis(sc.nextLong());
        }
        if(sc.hasNext()){
            millis += TimeUnit.HOURS.toMillis(sc.nextLong());
        }
        if(sc.hasNext()){
            millis += TimeUnit.MINUTES.toMillis(sc.nextLong());
        }
        this.maxDurationMilliseconds = millis;
        sc.close();
    }

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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public EquipmentState getState() {
        return state == null ? getState(new Date(), new Date()) : state;
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

    public void addComment(EquipmentComment comment){
        comment.setEquipment(this);
        comments.add(comment);
    }

    public void removeComment(EquipmentComment comment){
        comments.remove(comment);
        if(comment != null){
            comment.setEquipment(null);
        }
    }

    public List<EquipmentManual> getManuals() {
        return manuals;
    }

    public void setManuals(List<EquipmentManual> manuals) {
        this.manuals = manuals;
    }

    public void addManual(EquipmentManual manual){
        manual.setEquipment(this);
        manuals.remove(manual);
    }

    public void removeManual(EquipmentManual manual){
        manuals.remove(manual);
        if(manual != null){
            manual.setEquipment(null);
        }
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

    public boolean isWithinMaxReservationDuration(Date startDate, Date endDate){
        return (endDate.getTime() - startDate.getTime()) <= maxDurationMilliseconds;
    }

    /**
     * Returns whether a equipment is available in a timeframe
     *
     * Assumes that the endDate > Startdate for any given reservation
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isAvailable(Date startDate, Date endDate){
        if(!returned){
            return false;
        }
        // TODO check all reservations(also groups), if any between  start & end return false
        for(Reservation reservation: this.reservations){
            if(reservation.getEquipment().contains(this)){
                if(!(reservation.getEndDate().getTime() < startDate.getTime() || endDate.getTime() < reservation.getStartDate().getTime())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOverdue(){
        // TODO
        return isAvailable(new Date(), new Date()) && !returned;
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
        return name;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }
}
