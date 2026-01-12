package enterprise.elroi.exceptions.authServiceExceptions;

public class UserAlreadyExistException extends UserCurrentLoginNotFoundException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
