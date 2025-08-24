package az.edu.asiouconferenceportal.service.common;

import az.edu.asiouconferenceportal.common.enums.ActivityAction;

public interface ActivityService {
    void log(ActivityAction action, String entityType, Long entityId, String details);
}


