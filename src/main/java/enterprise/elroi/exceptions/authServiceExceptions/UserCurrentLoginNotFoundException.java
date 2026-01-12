package enterprise.elroi.exceptions.authServiceExceptions;

public class UserCurrentLoginNotFoundException extends UserIsNotAnAdminException{
    public UserCurrentLoginNotFoundException(String message) {
        super(message);
    }
}
