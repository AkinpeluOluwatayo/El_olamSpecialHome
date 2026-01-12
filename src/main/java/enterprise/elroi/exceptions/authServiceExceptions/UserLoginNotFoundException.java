package enterprise.elroi.exceptions.authServiceExceptions;

public class UserLoginNotFoundException extends RuntimeException {
  public UserLoginNotFoundException(String message) {
    super(message);
  }
}
