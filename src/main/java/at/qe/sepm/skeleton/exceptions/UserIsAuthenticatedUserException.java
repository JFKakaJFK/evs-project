package at.qe.sepm.skeleton.exceptions;

public class UserIsAuthenticatedUserException extends UserDeletionException {

    public UserIsAuthenticatedUserException(String message){
        super(message);
    }
}
