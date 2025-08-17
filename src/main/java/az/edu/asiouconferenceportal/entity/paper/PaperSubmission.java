package az.edu.asiouconferenceportal.entity.paper;

import az.edu.asiouconferenceportal.common.enums.PaperStatus;
import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import az.edu.asiouconferenceportal.entity.file.StoredFile;
import az.edu.asiouconferenceportal.entity.reference.PaperTypeEntity;
import az.edu.asiouconferenceportal.entity.reference.Topic;
import az.edu.asiouconferenceportal.entity.user.User;
import jakarta.persistence.*;
import java.util.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "papers")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaperSubmission extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String keywords;

	@Column(nullable = false, length = 4000)
	private String paperAbstract;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaperStatus status = PaperStatus.DRAFT;

	@ManyToOne(optional = false)
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne(optional = false)
	@JoinColumn(name = "paper_type_id")
	private PaperTypeEntity paperType;

	@ManyToMany
	@JoinTable(name = "paper_topics",
		joinColumns = @JoinColumn(name = "paper_id"),
		inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private Set<Topic> topics = new HashSet<>();

	@OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CoAuthor> coAuthors = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "file_id")
	private StoredFile pdf;
}
