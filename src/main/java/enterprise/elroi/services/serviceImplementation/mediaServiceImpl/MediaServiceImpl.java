package enterprise.elroi.services.serviceImplementation.mediaServiceImpl;

import enterprise.elroi.data.model.Media;
import enterprise.elroi.data.repository.MediaRepository;
import enterprise.elroi.dto.response.MediaResponse;
import enterprise.elroi.exceptions.mediaServiceException.MediaRecordNotFoundCanNotDelete;
import enterprise.elroi.services.mediaService.MediaServiceInterface;
import enterprise.elroi.utils.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
    public MediaResponse uploadMedia(MultipartFile file, String childId) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            Media media = new Media();
            media.setChildId(childId);

            // FIXED: Create a new ArrayList instead of force-casting singletonList
            List<String> images = new ArrayList<>();
            images.add("data:" + file.getContentType() + ";base64," + base64Image);
            media.setImages((ArrayList<String>) images);

            media.setUploadDate(LocalDateTime.now());

            Media savedMedia = mediaRepository.save(media);
            return mediaMapper.toMediaResponse(savedMedia);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image file", e);
        }
    }

    @Override
    public List<MediaResponse> getMediaByChildId(String childId) {
        return mediaRepository.findByChildId(childId).stream()
                .map(mediaMapper::toMediaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedia(String mediaId) {
        if (!mediaRepository.existsById(mediaId)) {
            throw new MediaRecordNotFoundCanNotDelete("Media record not found");
        }
        mediaRepository.deleteById(mediaId);
    }
}