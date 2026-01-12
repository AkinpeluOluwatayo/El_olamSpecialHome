package enterprise.elroi.exceptions.authServiceExceptions;

public class InvalidPasswordException extends UserLoginNotFoundException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
