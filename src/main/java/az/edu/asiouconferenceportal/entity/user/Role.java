package az.edu.asiouconferenceportal.entity.user;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
	@Column(name = "name", nullable = false, unique = true, length = 64)
	private String name;
}
