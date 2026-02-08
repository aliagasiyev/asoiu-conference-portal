package az.edu.asiouconferenceportal.controller.admin;

import az.edu.asiouconferenceportal.dto.user.CreateReviewerRequest;
import az.edu.asiouconferenceportal.entity.user.Role;
import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.repository.user.RoleRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reviewers")
    public ResponseEntity<ReviewerCreatedResponse> createReviewer(@Valid @RequestBody CreateReviewerRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        Role reviewer = roleRepository.findByName("REVIEWER").orElseGet(() -> {
            Role r = new Role();
            r.setName("REVIEWER");
            return roleRepository.save(r);
        });
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.getRoles().add(reviewer);
        u = userRepository.save(u);
        ReviewerCreatedResponse resp = new ReviewerCreatedResponse();
        resp.setId(u.getId());
        resp.setEmail(u.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    public static class ReviewerCreatedResponse {
        private Long id;
        private String email;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}


