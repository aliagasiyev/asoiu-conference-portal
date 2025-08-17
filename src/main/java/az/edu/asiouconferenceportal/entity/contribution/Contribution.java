package az.edu.asiouconferenceportal.entity.contribution;

import az.edu.asiouconferenceportal.common.enums.ContributionRole;
import az.edu.asiouconferenceportal.common.enums.SpeechType;
import az.edu.asiouconferenceportal.common.enums.TimeScope;
import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import az.edu.asiouconferenceportal.entity.user.User;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "contributions")
@Data
@EqualsAndHashCode(callSuper = true)
public class Contribution extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String keywords;

	@Column(nullable = false, length = 4000)
	private String description;

	@Column(nullable = false, length = 2000)
	private String bio;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SpeechType speechType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TimeScope timeScope;

	@Column(nullable = false)
	private String audience;

	private String previousTalkUrl;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "contribution_roles", joinColumns = @JoinColumn(name = "contribution_id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Set<ContributionRole> roles = new HashSet<>();
}
