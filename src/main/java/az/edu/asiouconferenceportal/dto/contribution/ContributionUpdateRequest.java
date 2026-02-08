package az.edu.asiouconferenceportal.dto.contribution;

import az.edu.asiouconferenceportal.common.enums.ContributionRole;
import az.edu.asiouconferenceportal.common.enums.SpeechType;
import az.edu.asiouconferenceportal.common.enums.TimeScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

import lombok.Data;

@Data
public class ContributionUpdateRequest {
    @NotEmpty
    private Set<ContributionRole> roles;
    @NotBlank
    private String title;
    @NotBlank
    private String keywords;
    @NotBlank
    private String description;
    @NotBlank
    private String bio;
    @NotNull
    private SpeechType speechType;
    @NotNull
    private TimeScope timeScope;
    @NotBlank
    private String audience;
    private String previousTalkUrl;
}
