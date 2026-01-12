package enterprise.elroi.exceptions.authServiceExceptions;

public class UserLoginNotFoundException extends UserAlreadyExistException {
    public UserLoginNotFoundException(String message) {
        super(message);
    }
}
