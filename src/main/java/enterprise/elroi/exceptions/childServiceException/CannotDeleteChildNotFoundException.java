package enterprise.elroi.exceptions.childServiceException;

public class CannotDeleteChildNotFoundException extends ChildNotFoundWithId {
    public CannotDeleteChildNotFoundException(String message) {
        super(message);
    }
}
