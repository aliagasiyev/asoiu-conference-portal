package az.edu.asiouconferenceportal.dto.contribution;

import az.edu.asiouconferenceportal.common.enums.ContributionRole;
import az.edu.asiouconferenceportal.common.enums.SpeechType;
import az.edu.asiouconferenceportal.common.enums.TimeScope;

import java.util.Set;

import lombok.Data;

@Data
public class ContributionResponse {
    private Long id;
    private Set<ContributionRole> roles;
    private String title;
    private String keywords;
    private String description;
    private String bio;
    private SpeechType speechType;
    private TimeScope timeScope;
    private String audience;
    private String previousTalkUrl;
}
