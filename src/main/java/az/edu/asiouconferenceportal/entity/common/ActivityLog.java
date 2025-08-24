package az.edu.asiouconferenceportal.entity.common;

import az.edu.asiouconferenceportal.common.enums.ActivityAction;
import az.edu.asiouconferenceportal.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "activity_logs")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityLog extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 64)
    private ActivityAction action;

    @Column(name = "entity_type", length = 64)
    private String entityType; // e.g., PAPER, REVIEW, USER

    @Column(name = "entity_id")
    private Long entityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(length = 1000)
    private String details;
}


