package enterprise.elroi.services.mediaService;

import enterprise.elroi.dto.response.MediaResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MediaServiceInterface {
    MediaResponse uploadMedia(MultipartFile file, String childId);
    List<MediaResponse> getMediaByChildId(String childId);
    void deleteMedia(String mediaId);
}