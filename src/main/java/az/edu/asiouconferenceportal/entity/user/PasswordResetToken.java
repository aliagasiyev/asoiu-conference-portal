package az.edu.asiouconferenceportal.entity.user;

import az.edu.asiouconferenceportal.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "password_reset_tokens")
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordResetToken extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String token;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private Instant expiresAt;
}


