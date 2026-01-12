package enterprise.elroi.services.childService;

import enterprise.elroi.dto.requests.ChildRequest;
import enterprise.elroi.dto.response.ChildResponse;
import java.util.List;

public interface ChildServiceInterface {
    ChildResponse addChild(ChildRequest request);
    ChildResponse updateChild(String childId, ChildRequest request);
    List<ChildResponse> getAllChildren();
    ChildResponse getChildById(String childId);
    void deleteChild(String childId);
}