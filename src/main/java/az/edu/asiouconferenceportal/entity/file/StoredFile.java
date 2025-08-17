package az.edu.asiouconferenceportal.entity.file;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "stored_files")
@Data
@EqualsAndHashCode(callSuper = true)
public class StoredFile extends BaseEntity {

	@Column(nullable = false)
	private String filename;

	@Column(nullable = false)
	private String contentType;

	@Column(nullable = false)
	private long size;

	@Column(nullable = false)
	private String path;
}
