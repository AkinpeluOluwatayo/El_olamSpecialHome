package enterprise.elroi.data.repository;

import enterprise.elroi.data.model.Child;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChildRepository extends MongoRepository<Child, String> {
}
