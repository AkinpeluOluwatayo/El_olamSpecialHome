package enterprise.elroi.exceptions.userServiceException;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
