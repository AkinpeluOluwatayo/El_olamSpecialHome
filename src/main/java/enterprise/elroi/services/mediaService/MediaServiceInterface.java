package enterprise.elroi.services.mediaService;

import enterprise.elroi.dto.requests.MediaRequest;
import enterprise.elroi.dto.response.MediaResponse;
import java.util.List;

public interface MediaServiceInterface {
    MediaResponse uploadMedia(MediaRequest request);
    List<MediaResponse> getMediaByChildId(String childId);
    void deleteMedia(String mediaId);
}