package az.edu.asiouconferenceportal.controller.admin;

import az.edu.asiouconferenceportal.common.enums.PaperStatus;
import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.repository.paper.PaperRepository;
import az.edu.asiouconferenceportal.service.paper.PaperService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/papers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaperController {

    private final PaperRepository paperRepository;
    private final PaperService paperService;

    @GetMapping
    public List<PaperResponse> listAll() {
        return paperRepository.findAll().stream().map(p -> paperService.getById(p.getId())).toList();
    }

    @GetMapping("/{id}")
    public PaperResponse get(@PathVariable Long id) {
        return paperService.getById(id);
    }

    @PostMapping("/{id}/technical-check")
    public PaperResponse technicalCheck(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean passed) {
        var p = paperRepository.findById(id).orElseThrow();
        p.setStatus(passed ? PaperStatus.UNDER_TECHNICAL_CHECK : PaperStatus.REJECTED);
        paperRepository.save(p);
        return paperService.getById(id);
    }

    @PostMapping("/{id}/final-decision")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalDecision(@PathVariable Long id, @RequestBody FinalDecisionBody body) {
        var p = paperRepository.findById(id).orElseThrow();
        p.setStatus(body.status);
        paperRepository.save(p);
    }

    @Data
    public static class FinalDecisionBody {
        @NotNull
        private PaperStatus status; // ACCEPTED / REJECTED / REVISIONS_REQUIRED
    }
}


