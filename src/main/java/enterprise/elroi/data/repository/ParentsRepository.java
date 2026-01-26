package enterprise.elroi.data.repository;

import enterprise.elroi.data.model.Parents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParentsRepository extends MongoRepository<Parents, String> {

    void deleteByChildId(String childId);
}