package az.edu.asiouconferenceportal.controller.admin;

import az.edu.asiouconferenceportal.entity.common.ActivityLog;
import az.edu.asiouconferenceportal.repository.common.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/activities")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminActivityController {

    private final ActivityLogRepository logRepository;

    @GetMapping
    public Page<ActivityLog> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return logRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }
}


