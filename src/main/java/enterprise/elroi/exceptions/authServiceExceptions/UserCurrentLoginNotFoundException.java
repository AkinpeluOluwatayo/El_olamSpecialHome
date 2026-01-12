package enterprise.elroi.exceptions.authServiceExceptions;

public class UserCurrentLoginNotFoundException extends RuntimeException {
  public UserCurrentLoginNotFoundException(String message) {
    super(message);
  }
}
