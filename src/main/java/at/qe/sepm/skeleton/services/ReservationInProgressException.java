package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.ReservationProperties;

public class ReservationInProgressException extends UserDeletionException {

    public ReservationInProgressException(String message){
        super(message);
    }
}
