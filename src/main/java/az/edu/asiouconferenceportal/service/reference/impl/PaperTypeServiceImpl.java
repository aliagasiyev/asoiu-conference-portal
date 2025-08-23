package az.edu.asiouconferenceportal.service.reference.impl;

import az.edu.asiouconferenceportal.dto.reference.PaperTypeRequest;
import az.edu.asiouconferenceportal.dto.reference.PaperTypeResponse;
import az.edu.asiouconferenceportal.entity.reference.PaperTypeEntity;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.reference.PaperTypeRepository;
import az.edu.asiouconferenceportal.service.reference.PaperTypeService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaperTypeServiceImpl implements PaperTypeService {

    private final PaperTypeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PaperTypeResponse> listActive() {
        return repository.findAllByActiveTrueOrderByNameAsc().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaperTypeResponse create(PaperTypeRequest request) {
        PaperTypeEntity e = new PaperTypeEntity();
        e.setName(request.getName());
        e.setActive(request.getActive() == null || request.getActive());
        return toResponse(repository.save(e));
    }

    @Override
    @Transactional
    public PaperTypeResponse update(Long id, PaperTypeRequest request) {
        PaperTypeEntity e = repository.findById(id).orElseThrow(() -> new NotFoundException("Paper type not found"));
        e.setName(request.getName());
        if (request.getActive() != null) e.setActive(request.getActive());
        return toResponse(e);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private PaperTypeResponse toResponse(PaperTypeEntity e) {
        PaperTypeResponse r = new PaperTypeResponse();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setActive(e.isActive());
        return r;
    }
}


