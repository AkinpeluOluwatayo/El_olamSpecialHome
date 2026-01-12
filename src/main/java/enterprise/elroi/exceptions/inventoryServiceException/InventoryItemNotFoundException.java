package enterprise.elroi.exceptions.inventoryException;

public class InventoryItemNotFoundException extends RuntimeException {
  public InventoryItemNotFoundException(String message) {
    super(message);
  }
}
