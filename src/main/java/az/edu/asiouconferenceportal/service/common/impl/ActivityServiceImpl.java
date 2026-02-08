package az.edu.asiouconferenceportal.service.common.impl;

import az.edu.asiouconferenceportal.common.enums.ActivityAction;
import az.edu.asiouconferenceportal.entity.common.ActivityLog;
import az.edu.asiouconferenceportal.repository.common.ActivityLogRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import az.edu.asiouconferenceportal.service.common.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityLogRepository logRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void log(ActivityAction action, String entityType, Long entityId, String details) {
        ActivityLog log = new ActivityLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                var user = userRepository.findByEmail(auth.getName()).orElse(null);
                log.setActor(user);
            }
        } catch (Exception ignored) {}
        log.setDetails(details);
        logRepository.save(log);
    }
}


