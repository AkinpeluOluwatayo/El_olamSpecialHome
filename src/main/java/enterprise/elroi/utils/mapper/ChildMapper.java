package enterprise.elroi.utils.mapper;

import enterprise.elroi.data.model.Child;
import enterprise.elroi.dto.requests.ChildRequest;
import enterprise.elroi.dto.response.ChildResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ChildMapper {

    public Child toChild(ChildRequest request) {
        if (request == null) return null;

        Child child = new Child();
        child.setName(request.getName());
        child.setAge(request.getAge());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setCondition(request.getCondition());
        child.setMedicalHistory(request.getMedicalHistory());

        child.setEnrollmentDate(LocalDateTime.now());

        return child;
    }

    public ChildResponse toChildResponse(Child child) {
        if (child == null) return null;

        ChildResponse response = new ChildResponse();
        response.setId(child.getId());
        response.setName(child.getName());
        response.setAge(child.getAge());
        response.setDateOfBirth(child.getDateOfBirth());
        response.setCondition(child.getCondition());
        response.setMedicalHistory(child.getMedicalHistory());

        return response;
    }
}