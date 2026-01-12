package enterprise.elroi.services.serviceImplementation.childServiceImpl;

import enterprise.elroi.data.model.Child;
import enterprise.elroi.data.repository.ChildRepository;
import enterprise.elroi.dto.requests.ChildRequest;
import enterprise.elroi.dto.response.ChildResponse;
import enterprise.elroi.exceptions.childServiceException.CannotDeleteChildNotFoundException;
import enterprise.elroi.exceptions.childServiceException.ChildNotFoundWithId;
import enterprise.elroi.exceptions.childServiceException.ChildRecordNotFoundException;
import enterprise.elroi.services.childService.ChildServiceInterface;
import enterprise.elroi.utils.mapper.ChildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl implements ChildServiceInterface {

    private final ChildRepository childRepository;
    private final ChildMapper childMapper;

    @Autowired
    public ChildServiceImpl(ChildRepository childRepository, ChildMapper childMapper) {
        this.childRepository = childRepository;
        this.childMapper = childMapper;
    }

    @Override
    public ChildResponse addChild(ChildRequest request) {
        Child child = childMapper.toChild(request);
        Child savedChild = childRepository.save(child);
        return childMapper.toChildResponse(savedChild);
    }

    @Override
    public ChildResponse updateChild(String childId, ChildRequest request) {
        Child existingChild = childRepository.findById(childId)
                .orElseThrow(() -> new ChildRecordNotFoundException("Child record not found with ID: " + childId));

        existingChild.setName(request.getName());
        existingChild.setAge(request.getAge());
        existingChild.setDateOfBirth(request.getDateOfBirth());
        existingChild.setCondition(request.getCondition());
        existingChild.setMedicalHistory(request.getMedicalHistory());

        Child updatedChild = childRepository.save(existingChild);
        return childMapper.toChildResponse(updatedChild);
    }

    @Override
    public List<ChildResponse> getAllChildren() {
        return childRepository.findAll().stream().map(childMapper::toChildResponse).collect(Collectors.toList());
    }

    @Override
    public ChildResponse getChildById(String childId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundWithId("Child not found with ID: " + childId));
        return childMapper.toChildResponse(child);
    }

    @Override
    public void deleteChild(String childId) {
        if (!childRepository.existsById(childId)) {
            throw new CannotDeleteChildNotFoundException("Cannot delete: Child not found");
        }
        childRepository.deleteById(childId);
    }
}