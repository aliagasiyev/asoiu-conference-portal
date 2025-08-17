package az.edu.asiouconferenceportal.entity.reference;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "topics")
@Data
@EqualsAndHashCode(callSuper = true)
public class Topic extends BaseEntity {

	@Column(nullable = false, unique = true, length = 200)
	private String name;

	@Column(nullable = false)
	private boolean active = true;

	@Column(name = "order_index")
	private Integer orderIndex;
}
