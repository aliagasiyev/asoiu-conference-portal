package az.edu.asiouconferenceportal.entity.paper;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "co_authors")
@Data
@EqualsAndHashCode(callSuper = true)
public class CoAuthor extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "paper_id")
    private PaperSubmission paper;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String affiliation;
    private String position;
    private String country;
    private String city;

    @PrePersist
    @PreUpdate
    private void sanitizeOptionalFields() {
        if (affiliation == null) affiliation = "";
        if (position == null) position = "";
        if (country == null) country = "";
        if (city == null) city = "";
    }
}
