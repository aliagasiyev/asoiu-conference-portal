package az.edu.asiouconferenceportal.entity.paper;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import az.edu.asiouconferenceportal.entity.user.User;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "review_assignments")
@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewAssignment extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "paper_id")
    private PaperSubmission paper;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Column(name = "due_at", nullable = false)
    private Instant dueAt;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "reminded_at")
    private Instant remindedAt;

    @Column(name = "completed_at")
    private Instant completedAt;
}


