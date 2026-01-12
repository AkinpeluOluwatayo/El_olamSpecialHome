package enterprise.elroi.exceptions.authServiceExceptions;

public class UserIsNotAnAdminException extends RuntimeException {
  public UserIsNotAnAdminException(String message) {
    super(message);
  }
}
