package az.edu.asiouconferenceportal.controller.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class UserController {

	@GetMapping
	public String me(@AuthenticationPrincipal UserDetails user) {
		return user != null ? user.getUsername() : "anonymous";
	}
}
