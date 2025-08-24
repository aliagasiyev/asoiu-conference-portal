package az.edu.asiouconferenceportal.service.review.impl;

import az.edu.asiouconferenceportal.repository.paper.ReviewAssignmentRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewReminderService {
    private static final Logger log = LoggerFactory.getLogger(ReviewReminderService.class);
    private final ReviewAssignmentRepository assignmentRepository;

    // Run every day at 09:00
    @Scheduled(cron = "0 0 9 * * *")
    public void sendReminders() {
        Instant now = Instant.now();
        Instant threshold = now.plus(Duration.ofDays(2));
        assignmentRepository.findAll().stream()
            .filter(a -> a.getCompletedAt() == null && a.getDueAt() != null && a.getDueAt().isBefore(threshold))
            .forEach(a -> {
                // For now, just log; integrate email/notification later
                log.warn("Assignment {} for reviewer {} due soon at {}", a.getId(), a.getReviewer().getEmail(), a.getDueAt());
            });
    }
}


