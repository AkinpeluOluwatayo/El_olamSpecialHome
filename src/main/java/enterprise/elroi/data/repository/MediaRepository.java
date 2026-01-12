package enterprise.elroi.data.repository;

import enterprise.elroi.data.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MediaRepository extends MongoRepository<Media, String> {
    List<Media> findByChildId(String childId);
}