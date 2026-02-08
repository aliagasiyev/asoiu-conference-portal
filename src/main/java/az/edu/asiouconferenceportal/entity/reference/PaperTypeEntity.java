package az.edu.asiouconferenceportal.entity.reference;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "paper_types")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaperTypeEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(nullable = false)
    private boolean active = true;
}
