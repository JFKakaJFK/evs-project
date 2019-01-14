package at.qe.sepm.skeleton.services;

public class UserIsAuthenticatedUserException extends UserDeletionException {

    public UserIsAuthenticatedUserException(String message){
        super(message);
    }
}
