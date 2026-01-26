package enterprise.elroi.controllers;

import enterprise.elroi.dto.response.MediaResponse;
import enterprise.elroi.services.mediaService.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/el_olam/media")
@CrossOrigin(origins = "http://localhost:5174")
public class MediaController {

    private final MediaServiceInterface mediaService;

    @Autowired
    public MediaController(MediaServiceInterface mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<MediaResponse> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("childId") String childId) {
        MediaResponse response = mediaService.uploadMedia(file, childId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<MediaResponse>> getMediaByChildId(@PathVariable("childId") String childId) {
        List<MediaResponse> mediaList = mediaService.getMediaByChildId(childId);
        return ResponseEntity.ok(mediaList);
    }

    @DeleteMapping("/delete/{mediaId}")
    public ResponseEntity<String> deleteMedia(@PathVariable("mediaId") String mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.ok("Media record deleted successfully");
    }
}