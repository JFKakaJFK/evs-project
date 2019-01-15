package at.qe.sepm.skeleton.exceptions;

public class ReservationInProgressException extends UserDeletionException {

    public ReservationInProgressException(String message){
        super(message);
    }
}
