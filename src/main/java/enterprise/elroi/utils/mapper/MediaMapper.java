package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.model.Media;
import enterprise.elroi.dto.requests.MediaRequest;
import enterprise.elroi.dto.response.MediaResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class MediaMapper {

    public Media toMedia(MediaRequest request) {
        if (request == null) return null;

        Media media = new Media();
        media.setChildId(request.getChildId());
        media.setImages(new ArrayList<>(request.getBase64Images()));
        media.setUploadDate(LocalDateTime.now());

        return media;
    }

    public MediaResponse toMediaResponse(Media media) {
        if (media == null) return null;

        MediaResponse response = new MediaResponse();
        response.setId(media.getId());
        response.setChildId(media.getChildId());
        response.setImages(media.getImages());
        response.setUploadDate(media.getUploadDate());

        return response;
    }
}