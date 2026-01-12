package enterprise.elroi.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;

    private String itemName;

    private String quantity;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private String lastUpdatedBy;
}