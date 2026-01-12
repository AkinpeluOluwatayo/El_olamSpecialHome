package enterprise.elroi.services.serviceImplementation.mediaServiceImpl;

import enterprise.elroi.data.model.Media;
import enterprise.elroi.data.repository.MediaRepository;
import enterprise.elroi.dto.requests.MediaRequest;
import enterprise.elroi.dto.response.MediaResponse;
import enterprise.elroi.exceptions.mediaServiceException.MediaRecordNotFoundCanNotDelete;
import enterprise.elroi.services.mediaService.MediaServiceInterface;
import enterprise.elroi.utils.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaServiceInterface {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public MediaResponse uploadMedia(MediaRequest request) {
        Media media = mediaMapper.toMedia(request);
        Media savedMedia = mediaRepository.save(media);
        return mediaMapper.toMediaResponse(savedMedia);
    }

    @Override
    public List<MediaResponse> getMediaByChildId(String childId) {
        return mediaRepository.findByChildId(childId).stream().map(mediaMapper::toMediaResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteMedia(String mediaId) {
        if (!mediaRepository.existsById(mediaId)) {
            throw new MediaRecordNotFoundCanNotDelete("Media record not found");
        }
        mediaRepository.deleteById(mediaId);
    }
}