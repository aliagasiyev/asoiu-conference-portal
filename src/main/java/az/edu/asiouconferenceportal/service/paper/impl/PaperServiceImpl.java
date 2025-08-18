package az.edu.asiouconferenceportal.service.paper.impl;

import az.edu.asiouconferenceportal.dto.paper.*;
import az.edu.asiouconferenceportal.entity.file.StoredFile;
import az.edu.asiouconferenceportal.entity.paper.CoAuthor;
import az.edu.asiouconferenceportal.entity.paper.PaperSubmission;
import az.edu.asiouconferenceportal.entity.reference.PaperTypeEntity;
import az.edu.asiouconferenceportal.entity.reference.Topic;
import az.edu.asiouconferenceportal.entity.reference.ConferenceSettings;
import az.edu.asiouconferenceportal.exception.NotFoundException;
import az.edu.asiouconferenceportal.repository.paper.CoAuthorRepository;
import az.edu.asiouconferenceportal.repository.paper.PaperRepository;
import az.edu.asiouconferenceportal.repository.reference.PaperTypeRepository;
import az.edu.asiouconferenceportal.repository.reference.TopicRepository;
import az.edu.asiouconferenceportal.repository.reference.ConferenceSettingsRepository;
import az.edu.asiouconferenceportal.repository.user.UserRepository;
import az.edu.asiouconferenceportal.service.file.FileStorageService;
import az.edu.asiouconferenceportal.service.paper.PaperService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {

	private final PaperRepository paperRepository;
	private final CoAuthorRepository coAuthorRepository;
	private final TopicRepository topicRepository;
	private final PaperTypeRepository paperTypeRepository;
	private final UserRepository userRepository;
	private final FileStorageService fileStorageService;
    private final ConferenceSettingsRepository settingsRepository;

	@Override
	@Transactional(readOnly = true)
	public List<PaperResponse> myPapers() {
		var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
		return paperRepository.findAllByAuthorOrderByCreatedAtDesc(user).stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaperResponse> myPapers(int page, int size) {
		var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
		return paperRepository.findAllByAuthorOrderByCreatedAtDesc(user, PageRequest.of(page, size))
			.map(this::toResponse)
			.getContent();
	}

	@Override
	@Transactional
	public PaperResponse create(PaperCreateRequest request) {
		var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
		PaperSubmission p = new PaperSubmission();
		p.setAuthor(user);
		applyRequestToPaper(p, request.getTitle(), request.getKeywords(), request.getPaperAbstract(), request.getPaperTypeId(), request.getTopicIds());
		paperRepository.save(p);
		if (request.getCoAuthors() != null) {
			for (var caReq : request.getCoAuthors()) {
				CoAuthor ca = new CoAuthor();
				ca.setPaper(p);
				ca.setFirstName(caReq.getFirstName());
				ca.setLastName(caReq.getLastName());
				ca.setEmail(caReq.getEmail());
				ca.setAffiliation(nonNull(caReq.getAffiliation()));
				ca.setPosition(nonNull(caReq.getPosition()));
				ca.setCountry(nonNull(caReq.getCountry()));
				ca.setCity(nonNull(caReq.getCity()));
				coAuthorRepository.save(ca);
				p.getCoAuthors().add(ca);
			}
		}
		return toResponse(p);
	}

	@Override
	@Transactional
	public PaperResponse update(Long id, PaperUpdateRequest request) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		applyRequestToPaper(p, request.getTitle(), request.getKeywords(), request.getPaperAbstract(), request.getPaperTypeId(), request.getTopicIds());
		p.getCoAuthors().clear();
		if (request.getCoAuthors() != null) {
			for (var caReq : request.getCoAuthors()) {
				CoAuthor ca = new CoAuthor();
				ca.setPaper(p);
				ca.setFirstName(caReq.getFirstName());
				ca.setLastName(caReq.getLastName());
				ca.setEmail(caReq.getEmail());
				ca.setAffiliation(caReq.getAffiliation());
				ca.setPosition(caReq.getPosition());
				ca.setCountry(caReq.getCountry());
				ca.setCity(caReq.getCity());
				coAuthorRepository.save(ca);
				p.getCoAuthors().add(ca);
			}
		}
		return toResponse(p);
	}

	@Override
	@Transactional
	public void delete(Long id) { paperRepository.deleteById(id); }

	@Override
	@Transactional
	public PaperResponse withdraw(Long id) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		p.setStatus(az.edu.asiouconferenceportal.common.enums.PaperStatus.WITHDRAWN);
		p.setWithdrawnAt(java.time.Instant.now());
		return toResponse(p);
	}

	@Override
	@Transactional
	public PaperResponse uploadPdf(Long id, MultipartFile file) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		try {
			StoredFile sf = fileStorageService.store(file);
			p.setPdf(sf);
			return toResponse(p);
		} catch (IOException e) {
			throw new RuntimeException("File upload failed");
		}
	}

	@Override
	@Transactional
	public PaperResponse uploadCameraReady(Long id, MultipartFile file) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		try {
			StoredFile sf = fileStorageService.store(file);
			p.setCameraReadyPdf(sf);
			return toResponse(p);
		} catch (IOException e) {
			throw new RuntimeException("File upload failed");
		}
	}

	@Override
	@Transactional
	public PaperResponse submit(Long id) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		ConferenceSettings s = settingsRepository.findAll().stream().findFirst().orElse(null);
		if (s != null && !s.isSubmissionsOpen()) throw new IllegalArgumentException("Submissions are closed");
		if (p.getPdf() == null || p.getTopics().isEmpty() || p.getPaperType() == null) {
			throw new IllegalArgumentException("Missing required fields to submit");
		}
		p.setStatus(az.edu.asiouconferenceportal.common.enums.PaperStatus.SUBMITTED);
		return toResponse(p);
	}

	@Override
	@Transactional
	public PaperResponse submitCameraReady(Long id) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		ConferenceSettings s = settingsRepository.findAll().stream().findFirst().orElse(null);
		if (s != null && !s.isCameraReadyOpen()) throw new IllegalArgumentException("Camera-ready submissions are closed");
		if (p.getCameraReadyPdf() == null) {
			throw new IllegalArgumentException("Camera-ready file required");
		}
		p.setStatus(az.edu.asiouconferenceportal.common.enums.PaperStatus.CAMERA_READY_SUBMITTED);
		return toResponse(p);
	}

	@Override
	@Transactional(readOnly = true)
	public PaperResponse getById(Long id) {
		PaperSubmission p = paperRepository.findById(id).orElseThrow(() -> new NotFoundException("Paper not found"));
		return toResponse(p);
	}

	private void applyRequestToPaper(PaperSubmission p, String title, String keywords, String paperAbstract, Long paperTypeId, List<Long> topicIds) {
		p.setTitle(title);
		p.setKeywords(keywords);
		p.setPaperAbstract(paperAbstract);
		PaperTypeEntity type = paperTypeRepository.findById(paperTypeId).orElseThrow(() -> new NotFoundException("Paper type not found"));
		p.setPaperType(type);
		var topics = topicRepository.findAllById(topicIds);
		p.getTopics().clear();
		p.getTopics().addAll(topics);
	}

	private PaperResponse toResponse(PaperSubmission p) {
		PaperResponse r = new PaperResponse();
		r.setId(p.getId());
		r.setTitle(p.getTitle());
		r.setKeywords(p.getKeywords());
		r.setPaperAbstract(p.getPaperAbstract());
		r.setStatus(p.getStatus());
		r.setPaperType(p.getPaperType() != null ? p.getPaperType().getName() : null);
		r.setTopics(p.getTopics().stream().map(Topic::getName).collect(Collectors.toList()));
		r.setCoAuthors(p.getCoAuthors().stream().map(c -> {
			CoAuthorRequest x = new CoAuthorRequest();
			x.setFirstName(c.getFirstName());
			x.setLastName(c.getLastName());
			x.setEmail(c.getEmail());
			x.setAffiliation(c.getAffiliation());
			x.setPosition(c.getPosition());
			x.setCountry(c.getCountry());
			x.setCity(c.getCity());
			return x;
		}).collect(Collectors.toList()));
		r.setFileId(p.getPdf() != null ? p.getPdf().getId() : null);
		r.setCameraReadyFileId(p.getCameraReadyPdf() != null ? p.getCameraReadyPdf().getId() : null);
		return r;
	}

	private String nonNull(String v) { return v == null ? "" : v; }

	@Override
	@Transactional
	public PaperResponse addCoAuthor(Long paperId, CoAuthorRequest request) {
		PaperSubmission p = paperRepository.findById(paperId).orElseThrow(() -> new NotFoundException("Paper not found"));
		CoAuthor ca = new CoAuthor();
		ca.setPaper(p);
		ca.setFirstName(request.getFirstName());
		ca.setLastName(request.getLastName());
		ca.setEmail(request.getEmail());
		ca.setAffiliation(nonNull(request.getAffiliation()));
		ca.setPosition(nonNull(request.getPosition()));
		ca.setCountry(nonNull(request.getCountry()));
		ca.setCity(nonNull(request.getCity()));
		coAuthorRepository.save(ca);
		p.getCoAuthors().add(ca);
		return toResponse(p);
	}

	@Override
	@Transactional
	public PaperResponse updateCoAuthor(Long paperId, Long coAuthorId, CoAuthorRequest request) {
		PaperSubmission p = paperRepository.findById(paperId).orElseThrow(() -> new NotFoundException("Paper not found"));
		CoAuthor ca = coAuthorRepository.findById(coAuthorId).orElseThrow(() -> new NotFoundException("Co-author not found"));
		if (!Objects.equals(ca.getPaper().getId(), p.getId())) throw new NotFoundException("Co-author not in paper");
		ca.setFirstName(request.getFirstName());
		ca.setLastName(request.getLastName());
		ca.setEmail(request.getEmail());
		ca.setAffiliation(nonNull(request.getAffiliation()));
		ca.setPosition(nonNull(request.getPosition()));
		ca.setCountry(nonNull(request.getCountry()));
		ca.setCity(nonNull(request.getCity()));
		return toResponse(p);
	}

	@Override
	@Transactional
	public void deleteCoAuthor(Long paperId, Long coAuthorId) {
		PaperSubmission p = paperRepository.findById(paperId).orElseThrow(() -> new NotFoundException("Paper not found"));
		CoAuthor ca = coAuthorRepository.findById(coAuthorId).orElseThrow(() -> new NotFoundException("Co-author not found"));
		if (!Objects.equals(ca.getPaper().getId(), p.getId())) throw new NotFoundException("Co-author not in paper");
		p.getCoAuthors().remove(ca);
		coAuthorRepository.delete(ca);
	}
}


