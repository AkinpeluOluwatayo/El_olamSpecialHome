package enterprise.elroi.exceptions.authServiceExceptions;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
