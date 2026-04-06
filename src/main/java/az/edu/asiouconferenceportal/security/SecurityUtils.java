package az.edu.asiouconferenceportal.security;

import az.edu.asiouconferenceportal.entity.user.User;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_REVIEWER = "ROLE_REVIEWER";

    
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("User is not authenticated");
        }
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new NotFoundException("Authenticated user record not found"));
    }

    /**
     * Safely retrieves the current user ID.
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Checks if the current user has the ADMIN role.
     */
    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .anyMatch(a -> ROLE_ADMIN.equals(a.getAuthority()));
    }

    /**
     * Checks if the current user has a specific role.
     */
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return auth.getAuthorities().stream()
                .anyMatch(a -> formattedRole.equals(a.getAuthority()));
    }

    /**
     * Enforces ownership based on User ID.
     * Throws AccessDeniedException if the current user is not the owner and not an admin.
     */
    public void verifyOwnershipOrAdmin(Long ownerId) {
        if (isAdmin()) return;
        if (!Objects.equals(getCurrentUserId(), ownerId)) {
            throw new AccessDeniedException("Access denied: You do not own this resource");
        }
    }

    /**
     * Enforces strict ownership based on User ID (Admins are NOT exempted).
     */
    public void verifyStrictOwnership(Long ownerId) {
        if (!Objects.equals(getCurrentUserId(), ownerId)) {
            throw new AccessDeniedException("Access denied: Only the owner can perform this action");
        }
    }
}
