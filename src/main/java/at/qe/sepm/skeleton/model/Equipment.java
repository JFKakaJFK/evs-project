package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.configs.ReservationProperties;
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

    @Column(nullable = false)
    private Long maxDurationMilliseconds;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EquipmentComment> comments;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EquipmentManual> manuals = new ArrayList<>();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "equipment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentReservation> reservations = new ArrayList<>();

    /**
     * Returns whether the the time frame from start to end is within the maximal reservation duration
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isWithinMaxReservationDuration(Date startDate, Date endDate){
        return (endDate.getTime() - startDate.getTime()) <= maxDurationMilliseconds;
    }

    /**
     * Returns an {@link EquipmentState} depending on the start and end Date, if the equipment is currently
     * {@link EquipmentState#OVERDUE}, but the time frame starts far enough in the future it will be {@link EquipmentState#AVAILABLE}
     *
     * @param start of the enquiry
     * @param end of the enquiry
     * @return {@link EquipmentState} for the period
     */
    public EquipmentState getFutureState(Date start, Date end){
        if(locked){
            return EquipmentState.LOCKED;
        } else if(isAvailable(start, end)){
            return EquipmentState.AVAILABLE;
        } else if(isOverdue()){
            return EquipmentState.OVERDUE;
        } else {
            return EquipmentState.BOOKED;
        }
    }

    /**
     * Returns an {@link EquipmentState} depending on the current date, in contrast to the other getFutureState method, this
     * method will NOT set currently {@link EquipmentState#OVERDUE} to {@link EquipmentState#AVAILABLE} in the future.
     *
     * @return
     */
    public EquipmentState getState() {
        Date now = new Date();
        if(locked){
            return EquipmentState.LOCKED;
        } else if(isOverdue()){
            return EquipmentState.OVERDUE;
        } else if(isAvailable(now, now)){
            return EquipmentState.AVAILABLE;
        }  else {
            return EquipmentState.BOOKED;
        }
    }

    /**
     * Returns whether a equipment is available in a timeframe, assuming
     *
     * Assumes that the endDate > Startdate for any given reservation
     * @param startDate
     * @param endDate
     * @return
     */
    private boolean isAvailable(Date startDate, Date endDate){
        for(EquipmentReservation reservation: this.reservations){
            if(reservation.isCompleted()){
                continue;
            }
            boolean reservationEndsBeforeStartDate = (reservation.getEndDate().getTime() + ReservationProperties.getNextReservationBuffer()) < startDate.getTime();
            boolean reservationStartsAfterEndDate = (endDate.getTime() + ReservationProperties.getNextReservationBuffer()) < reservation.getStartDate().getTime();
            if (!(reservationEndsBeforeStartDate || reservationStartsAfterEndDate)){
                return false;
            }
            if(reservation.isOverdue()){
                if(!((reservation.getEndDateOverdue().getTime() + ReservationProperties.getOverdueBuffer()) < startDate.getTime())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the equipment is overdue(should be available but is not yet returned)
     *
     * @return
     */
    private boolean isOverdue(){
        for(EquipmentReservation reservation: reservations){
            if(!reservation.isCompleted() && reservation.getEndDate().before(new Date())){
                return true;
            }
        }
        return false;
    }

    /* ManyToOne cascading methods */

    /**
     * Safely add an EquipmentComment
     *
     * @param comment
     */
    public void addComment(EquipmentComment comment){
        comment.setEquipment(this);
        comments.add(comment);
    }

    /**
     * Safely remove an EquipmentComment
     *
     * @param comment
     */
    public void removeComment(EquipmentComment comment){
        comments.remove(comment);
        if(comment != null){
            comment.setEquipment(null);
        }
    }

    /**
     * Safely add an EquipmentManual
     *
     * @param manual
     */
    public void addManual(EquipmentManual manual){
        manual.setEquipment(this);
        manuals.add(manual);
    }

    /**
     * Safely remove an EquipmentManual
     *
     * @param manual
     */
    public void removeManual(EquipmentManual manual){
        manuals.remove(manual);
        if(manual != null){
            manual.setEquipment(null);
        }
    }

    /**
     * Safely add an EquipmentReservation
     *
     * @param reservation
     */
    public void addReservation(EquipmentReservation reservation){
        reservation.setEquipment(this);
        reservations.add(reservation);
    }

    /**
     * Safely remove an EquipmentReservation
     *
     * @param reservation
     */
    public void removeReservation(EquipmentReservation reservation){
        reservations.remove(reservation);
        if(reservation != null){
            reservation.setEquipment(null);
        }
    }

    /* Getters & Setters */

    /**
     * Returns the maximal reservation duration in the format DDTage hh:mmStd.
     *
     * @return formatted string
     */
    public String getMaxDurationFormatted(){
        return String.format("%dTage %d:%dStd.",
            TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds),
            TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds)),
            TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds))
        );
    }

    /**
     * Returns the maximal reservation duration as string
     *
     * @return DD:hh:mm
     */
    public String getMaxDuration(){
        return String.format("%2d:2%d:%2d",
            TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds),
            TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(maxDurationMilliseconds)),
            TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(maxDurationMilliseconds))
        );
    }

    /**
     * Parses a string with the format DD:hh:mm and sets the maximal reservation duration
     *
     * @param duration
     */
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

    public List<EquipmentReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<EquipmentReservation> reservations) {
        this.reservations = reservations;
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
