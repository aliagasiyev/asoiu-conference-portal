package az.edu.asiouconferenceportal.service.contribution.impl;

import az.edu.asiouconferenceportal.dto.contribution.*;
import az.edu.asiouconferenceportal.entity.contribution.Contribution;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.contribution.ContributionRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import az.edu.asiouconferenceportal.service.contribution.ContributionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContributionServiceImpl implements ContributionService {

	private final ContributionRepository repository;
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ContributionResponse> myContributions() {
		var email = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
		return repository.findAllByUserOrderByCreatedAtDesc(user).stream()
			.map(this::toResponse)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ContributionResponse create(ContributionCreateRequest request) {
		var email = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
		Contribution c = new Contribution();
		c.setUser(user);
		apply(c, request.getRoles(), request.getTitle(), request.getKeywords(), request.getDescription(), request.getBio(), request.getSpeechType(), request.getTimeScope(), request.getAudience(), request.getPreviousTalkUrl());
		return toResponse(repository.save(c));
	}

	@Override
	@Transactional
	public ContributionResponse update(Long id, ContributionUpdateRequest request) {
		Contribution c = repository.findById(id).orElseThrow(() -> new NotFoundException("Contribution not found"));
		apply(c, request.getRoles(), request.getTitle(), request.getKeywords(), request.getDescription(), request.getBio(), request.getSpeechType(), request.getTimeScope(), request.getAudience(), request.getPreviousTalkUrl());
		return toResponse(c);
	}

	@Override
	@Transactional
	public void delete(Long id) { repository.deleteById(id); }

	private void apply(Contribution c, java.util.Set<az.edu.asiouconferenceportal.common.enums.ContributionRole> roles, String title, String keywords, String description, String bio, az.edu.asiouconferenceportal.common.enums.SpeechType speechType, az.edu.asiouconferenceportal.common.enums.TimeScope timeScope, String audience, String previousTalkUrl) {
		c.setRoles(roles);
		c.setTitle(title);
		c.setKeywords(keywords);
		c.setDescription(description);
		c.setBio(bio);
		c.setSpeechType(speechType);
		c.setTimeScope(timeScope);
		c.setAudience(audience);
		c.setPreviousTalkUrl(previousTalkUrl);
	}

	private ContributionResponse toResponse(Contribution c) {
		ContributionResponse r = new ContributionResponse();
		r.setId(c.getId());
		r.setRoles(c.getRoles());
		r.setTitle(c.getTitle());
		r.setKeywords(c.getKeywords());
		r.setDescription(c.getDescription());
		r.setBio(c.getBio());
		r.setSpeechType(c.getSpeechType());
		r.setTimeScope(c.getTimeScope());
		r.setAudience(c.getAudience());
		r.setPreviousTalkUrl(c.getPreviousTalkUrl());
		return r;
	}
}


