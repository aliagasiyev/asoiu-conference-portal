package az.edu.asiouconferenceportal.controller.user;

import az.edu.asiouconferenceportal.dto.paper.PaperResponse;
import az.edu.asiouconferenceportal.dto.contribution.ContributionResponse;
import az.edu.asiouconferenceportal.service.contribution.ContributionService;
import az.edu.asiouconferenceportal.service.paper.PaperService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final PaperService paperService;
    private final ContributionService contributionService;

    @GetMapping
    public Map<String, Object> home() {
        List<PaperResponse> papers = paperService.myPapers(0, 50);
        List<ContributionResponse> contribs = contributionService.myContributions(0, 50);
        return Map.of(
                "submittedPapers", papers,
                "contributions", contribs
        );
    }
}


