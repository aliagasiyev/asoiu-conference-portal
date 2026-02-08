package az.edu.asiouconferenceportal.entity.paper;

import az.edu.asiouconferenceportal.common.enums.ReviewDecision;
import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import az.edu.asiouconferenceportal.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "paper_reviews")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaperReview extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "assignment_id")
    private ReviewAssignment assignment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewDecision decision;

    @Column(length = 4000)
    private String comments;
}


